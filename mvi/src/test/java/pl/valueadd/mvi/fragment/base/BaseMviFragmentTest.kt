package pl.valueadd.mvi.fragment.base

/* ktlint-disable no-wildcard-imports */
import android.view.View
import androidx.viewbinding.ViewBinding
import io.mockk.*
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.parcel.Parcelize
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import pl.valueadd.mvi.IBaseViewState
import pl.valueadd.mvi.activity.ActivityBindingInflater
import pl.valueadd.mvi.activity.BaseActivity
import pl.valueadd.mvi.presenter.BaseMviPresenter
import pl.valueadd.mvi.presenter.IBasePartialState
import pl.valueadd.mvi.presenter.IBaseView

class BaseMviFragmentTest {

    private lateinit var mockPresenter: TestPresenter

    private lateinit var fragment: TestMviFragment

    @BeforeEach
    fun setup() {
        mockPresenter = mockk(relaxed = true)
        fragment = TestMviFragment()
        fragment.presenter = mockPresenter
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    @Disabled("There is a need to hack attaching activity to child fragment manager but at this moment it is package scoped")
    fun `Should call initialize state on presenter on create`() {
        // Given
        val mockActivity = mockk<TestActivity>(relaxed = true)
        fragment.onAttach(mockActivity)

        // When
        fragment.onCreate(null)

        // Then
        verify(exactly = 1) { mockPresenter.initializeState(fragment) }
    }

    @Test
    fun `Should attach view to presenter on start`() {
        // Given

        // When
        fragment.onStart()

        // Then
        verify(exactly = 1) { mockPresenter.attachView(fragment) }
    }

    @Test
    fun `Should detach view to presenter on stop`() {
        // Given

        // When
        fragment.onStop()

        // Then
        verify(exactly = 1) { mockPresenter.detachView() }
    }

    @Test
    fun `Should call onDestroy on presenter`() {
        // Given
        val mockActivity = mockk<TestActivity>(relaxed = true)
        fragment.onAttach(mockActivity)

        // When
        fragment.onDestroy()

        // Then
        verify(exactly = 1) { mockPresenter.destroy() }
    }
}

private class TestMviFragment :
    BaseMviFragment<TestView, TestViewState, TestViewIntent, TestViewEffect, TestPresenter, TestFragmentViewBinding>(),
    TestView {

    override lateinit var presenter: TestPresenter

    override val bindingInflater: FragmentBindingInflater<TestFragmentViewBinding>
        get() = mockk(relaxed = true)

    override fun render(state: TestViewState) {
        // no-op
    }

    override fun provideViewIntents(): List<Observable<TestViewIntent>> {
        return emptyList()
    }

    override fun provideInitialViewState() = TestViewState()
}

private class TestPresenter(
    private val mapper: TestViewIntentToPartialStateMapper,
    private val reducer: TestReducer
) : BaseMviPresenter<TestViewState, TestPartialState, TestViewIntent, TestViewEffect, TestView>(Schedulers.trampoline()) {
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
}

private interface TestView :
    IBaseView<TestViewState, TestViewIntent, TestViewEffect>

@Parcelize
private class TestViewState(var someProperty: Int = 0) :
    IBaseViewState

private class TestPartialState(var someProperty: Int) :
    IBasePartialState

private class TestViewIntent : IBaseView.IBaseIntent

private class TestViewEffect : IBaseView.IBaseEffect

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

private class TestActivity : BaseActivity<TestActivityViewBinding>() {
    override val bindingInflater: ActivityBindingInflater<TestActivityViewBinding>
        get() = mockk(relaxed = true)
}

private class TestFragmentViewBinding : ViewBinding {
    override fun getRoot(): View {
        return mockk(relaxed = true)
    }
}

private class TestActivityViewBinding : ViewBinding {
    override fun getRoot(): View {
        return mockk(relaxed = true)
    }
}