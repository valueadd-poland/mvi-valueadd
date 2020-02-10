package pl.valueadd.mvi.fragment.mvi

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import pl.valueadd.mvi.exception.ViewWasNotDetachedException

@ExtendWith(MockKExtension::class)
class BaseMviPresenterTest {

    @MockK
    private lateinit var mockMapper: TestViewIntentToPartialStateMapper

    @MockK
    private lateinit var mockReducer: TestReducer

    private lateinit var presenter: TestPresenter

    private lateinit var presenterPublishSubject: PublishSubject<TestPartialState>

    @BeforeEach
    private fun setUp() {
        presenterPublishSubject = PublishSubject.create<TestPartialState>()
        presenter = TestPresenter(mockMapper, mockReducer, presenterPublishSubject)
    }

    @Test
    fun `Should throw exception when old view was not detached before attaching new one`() {
        // Given
        val firstView: IBaseView<TestViewState, TestViewIntent> = mockk {
            every { provideViewIntents() } returns emptyList()
            every { render(any()) } answers {}
        }
        val secondView: IBaseView<TestViewState, TestViewIntent> = mockk()

        presenter.attachView(firstView)

        // When
        // Then
        Assertions.assertThrows(ViewWasNotDetachedException::class.java) {
            presenter.attachView(secondView)
        }
    }

    @Test
    fun `Should start observing view intent on view attach`() {
        // Given
        val viewIntentsSubject = PublishSubject.create<TestViewIntent>()
        val mockView: IBaseView<TestViewState, TestViewIntent> = mockk {
            every { provideViewIntents() } returns listOf(viewIntentsSubject)
            every { render(any()) } answers {}
        }

        // When
        presenter.attachView(mockView)

        // Then
        verify(exactly = 1) { mockView.provideViewIntents() }
        assert(viewIntentsSubject.hasObservers())
    }

    @Test
    fun `Should stop observing view intent on view detach`() {
        // Given
        val viewIntentsSubject = PublishSubject.create<TestViewIntent>()
        val mockView: IBaseView<TestViewState, TestViewIntent> = mockk {
            every { provideViewIntents() } returns listOf(viewIntentsSubject)
            every { render(any()) } answers {}
        }
        presenter.attachView(mockView)

        // When
        presenter.detachView()

        // Then
        verify(exactly = 1) { mockView.provideViewIntents() }
        assert(viewIntentsSubject.hasObservers() == false)
    }

    @Test
    fun `Should not stop observing presenter intents after view detach`() {
        // Given
        val testPresenterPartialState = TestPartialState(1)
        val expectedTestViewState = TestViewState(2)
        val mockView: IBaseView<TestViewState, TestViewIntent> = mockk {
            every { provideViewIntents() } returns listOf()
            every { render(any()) } answers {}
        }
        every { mockReducer.reduce(any(), testPresenterPartialState) } returns expectedTestViewState
        presenter.attachView(mockView)

        // When
        presenter.detachView()
        presenterPublishSubject.onNext(testPresenterPartialState)

        // Then
        assert(presenterPublishSubject.hasObservers())
        assert(presenter.currentState == expectedTestViewState)
    }

    @Test
    fun `Should call render after new view intent`() {
        // Given
        val testPartialStatePublishSubject = PublishSubject.create<TestPartialState>()
        val viewIntentsSubject = PublishSubject.create<TestViewIntent>()
        val testViewIntent = TestViewIntent()
        val testPartialState = TestPartialState(1)
        val mockView: IBaseView<TestViewState, TestViewIntent> = mockk {
            every { provideViewIntents() } returns listOf(viewIntentsSubject)
            every { render(any()) } answers {}
        }
        val reducedViewState = TestViewState(1)
        every { mockMapper.mapViewIntentToPartialState(testViewIntent) } returns testPartialStatePublishSubject
        every { mockReducer.reduce(any(), testPartialState) } returns reducedViewState
        presenter.attachView(mockView)
        viewIntentsSubject.onNext(testViewIntent) // For example user press login button

        // When
        testPartialStatePublishSubject.onNext(testPartialState) // Login response is returned

        // Then
        verify(exactly = 1) { mockView.render(reducedViewState) }
    }

    @Test
    fun `Should not call render after view detach`() {
        // Given
        val testPartialStatePublishSubject = PublishSubject.create<TestPartialState>()
        val viewIntentsSubject = PublishSubject.create<TestViewIntent>()
        val testViewIntent = TestViewIntent()
        val testPartialState = TestPartialState(0)
        val mockView: IBaseView<TestViewState, TestViewIntent> = mockk {
            every { provideViewIntents() } returns listOf(viewIntentsSubject)
            every { render(any()) } answers {}
        }
        val reducedViewState = TestViewState()
        every { mockMapper.mapViewIntentToPartialState(testViewIntent) } returns testPartialStatePublishSubject
        every { mockReducer.reduce(any(), testPartialState) } returns reducedViewState
        presenter.attachView(mockView)
        viewIntentsSubject.onNext(testViewIntent) // For example user press login button
        presenter.detachView() // View of fragment is destroyed by system

        // When
        testPartialStatePublishSubject.onNext(testPartialState) // Login response is returned

        // Then
        verify(exactly = 0) { mockView.render(reducedViewState) }
    }

    @Test
    fun `After attach view should call render with latest view state`() {
        // Given
        val testPartialStatePublishSubject = PublishSubject.create<TestPartialState>()
        val viewIntentsSubject = PublishSubject.create<TestViewIntent>()
        val testViewIntent = TestViewIntent()
        val testPartialState = TestPartialState(1)
        val reducedViewState = TestViewState(1)
        val mockView: IBaseView<TestViewState, TestViewIntent> = mockk {
            every { provideViewIntents() } returns listOf(viewIntentsSubject)
            every { render(any()) } answers {}
        }
        every { mockMapper.mapViewIntentToPartialState(testViewIntent) } returns testPartialStatePublishSubject
        every { mockReducer.reduce(any(), testPartialState) } returns reducedViewState
        presenter.attachView(mockView)
        viewIntentsSubject.onNext(testViewIntent) // For example user press login button

        presenter.detachView() // User rotate screen
        testPartialStatePublishSubject.onNext(testPartialState) // Login response is returned

        // When
        presenter.attachView(mockView)

        // Then
        verify(exactly = 1) { mockView.render(reducedViewState) }
    }

    @Test
    fun `Should not stop processing pending view intents on detach`() {
        // Given
        val testPartialStatePublishSubject = PublishSubject.create<TestPartialState>()
        val viewIntentsSubject = PublishSubject.create<TestViewIntent>()
        val testViewIntent = TestViewIntent()
        val testPartialState = TestPartialState(1)
        val reducedViewState = TestViewState(1)
        val mockView: IBaseView<TestViewState, TestViewIntent> = mockk {
            every { provideViewIntents() } returns listOf(viewIntentsSubject)
            every { render(any()) } answers {}
        }
        every { mockMapper.mapViewIntentToPartialState(testViewIntent) } returns testPartialStatePublishSubject
        every { mockReducer.reduce(any(), testPartialState) } returns reducedViewState
        presenter.attachView(mockView)
        viewIntentsSubject.onNext(testViewIntent) // For example user press login button

        // When
        presenter.detachView()

        // Then
        assert(testPartialStatePublishSubject.hasObservers())
    }

    @Test
    fun `Should stop processing pending view intents and presenter intents on destroy`() {
        // Given
        val testPartialStatePublishSubject = PublishSubject.create<TestPartialState>()
        val viewIntentsSubject = PublishSubject.create<TestViewIntent>()
        val testViewIntent = TestViewIntent()
        val testPartialState = TestPartialState(1)
        val reducedViewState = TestViewState(1)
        val mockView: IBaseView<TestViewState, TestViewIntent> = mockk {
            every { provideViewIntents() } returns listOf(viewIntentsSubject)
            every { render(any()) } answers {}
        }
        every { mockMapper.mapViewIntentToPartialState(testViewIntent) } returns testPartialStatePublishSubject
        every { mockReducer.reduce(any(), testPartialState) } returns reducedViewState
        presenter.attachView(mockView)
        viewIntentsSubject.onNext(testViewIntent) // For example user press login button

        // When
        presenter.destroy()

        // Then
        assert(testPartialStatePublishSubject.hasObservers() == false)
        assert(presenterPublishSubject.hasObservers() == false)
    }
}

private class TestPresenter(
    private val mapper: TestViewIntentToPartialStateMapper,
    private val reducer: TestReducer,
    private val presenterObservable: Observable<TestPartialState>
) : BaseMviPresenter<TestViewState, TestPartialState, TestViewIntent, IBaseView<TestViewState, TestViewIntent>>(
    TestViewState()
) {
    override val viewStateSubscriptionScheduler = Schedulers.trampoline()
    override val viewStateObservationScheduler = Schedulers.trampoline()

    override fun mapViewIntentToPartialState(viewIntent: TestViewIntent): Observable<out TestPartialState> {
        // For easier testing
        return mapper.mapViewIntentToPartialState(viewIntent)
    }

    override fun reduce(previousState: TestViewState, action: TestPartialState): TestViewState {
        // For easier testing
        return reducer.reduce(previousState, action)
    }

    override fun providePresenterIntents(): List<Observable<out TestPartialState>> {
        return listOf(presenterObservable)
    }
}

private class TestViewState(var someProperty: Int = 0) : IBaseViewState

private class TestPartialState(var someProperty: Int) : IBasePartialState

private class TestViewIntent : IBaseView.IBaseIntent

private class TestViewIntentToPartialStateMapper {
    fun mapViewIntentToPartialState(viewIntent: TestViewIntent): Observable<out TestPartialState> {
        throw RuntimeException("Should be mocked")
    }
}

private class TestReducer {
    fun reduce(previousState: TestViewState, action: TestPartialState): TestViewState {
        throw RuntimeException("Should be mocked")
    }
}