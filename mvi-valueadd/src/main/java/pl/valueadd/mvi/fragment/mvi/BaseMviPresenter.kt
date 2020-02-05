package pl.valueadd.mvi.fragment.mvi

import androidx.annotation.CallSuper
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import pl.valueadd.mvi.exception.ViewNotAttachedException

abstract class BaseMviPresenter<VS : IBaseViewState, PS : IBasePartialState, VI : IBaseView.IBaseIntent, V : IBaseView<VS, VI>>(
    initialState: VS
) : IMviPresenter<V> {

    /**
     * Current view state.
     */
    var currentState: VS = initialState
        private set

    /**
     * Cold observable of [view state][IBaseViewState].
     */
    val stateObservable: Observable<VS>
        get() = viewStateBehaviorSubject

    /**
     * Returns [view][IBaseView] but may throw [ViewNotAttachedException] if called in wrong place.
     */
    protected val view: V
        get() = internalView ?: throw ViewNotAttachedException()

    /**
     * @see subscribeViewStateConsumer
     * @see viewStateConsumerDisposable
     */
    protected open val viewStateSubscriptionScheduler: Scheduler =
        Schedulers.io()

    /**
     * Internal nullable [view][IBaseView] of presenter.
     */
    private var internalView: V? = null

    /**
     * Use to determine when a intents have to be binded.
     */
    private var isViewAttachedFirstTime = true

    /**
     * A disposable container of temporarily binded view intents.
     */
    private var currentViewIntentsDisposable: Disposable? = null

    /**
     * A disposable of wrapped intents which use to reduce a [view state][IBaseViewState]
     */
    private var viewStateReducerDisposable: Disposable? = null

    /**
     * A disposable of currently binded consumer for emission of wrapped intents.
     */
    private var viewStateConsumerDisposable: Disposable? = null

    /**
     * A subject to pass emission of wrapped intents to currently binded view's consumer.
     */
    private val viewStateBehaviorSubject =
        BehaviorSubject.createDefault(currentState)

    /**
     * A subject to wrap view's intents emission.
     */
    private val currentViewIntentsSubject =
        PublishSubject.create<VI>()

    /**
     * Contains disposable subscription of streams.
     */
    final override val disposables: CompositeDisposable
            by lazy { CompositeDisposable() }

    /**
     * Add subscription to composite.
     */
    final override fun addDisposable(disposable: Disposable): Boolean =
        disposables.add(disposable)

    /**
     * @see [Disposable.isDisposed]
     */
    final override fun isDisposed(): Boolean =
        disposables.isDisposed

    /**
     * @see [Disposable.isDisposed]
     */
    final override fun dispose(): Unit =
        disposables.dispose()

    @CallSuper
    override fun attachView(view: V) {
        this.internalView = view

        if (isViewAttachedFirstTime) {
            subscribeViewIntents()
        }

        subscribeViewStateConsumer()

        bindIntents(view)

        isViewAttachedFirstTime = false
    }

    @CallSuper
    override fun detachView() {
        this.internalView = null

        disposeViewStateConsumer()

        disposeCurrentViewIntents()
    }

    @CallSuper
    override fun destroy() {
        dispose()
        reset()
    }

    protected abstract fun mapViewIntentToPartialState(viewIntent: VI): Observable<out PS>

    /**
     * A consumer responsible for handling transition from previous state to next state of your view.
     */
    protected abstract fun reduce(previousState: VS, action: PS): VS

    /**
     * Provides presenter's intents which use by [reducer][BaseMviPresenter.reduce].
     *
     * This method should be called **once**. The best time to call it when view is attached for the first time.
     *
     * Example implementation:
     *
     * ```
     * override fun providePresenterIntents(): List<Observable<out SampleView.PartialState>> = listOf(
     *      createSampleIntent(),
     *      createExampleIntent(),
     *      createDummyIntent()
     * )
     * ```
     */
    protected open fun providePresenterIntents(): Observable<out PS> = Observable.empty()

    /**
     * Binds provided view's intents to wrapper subject.
     *
     * This method should be called **every time** when view is attached.
     *
     */
    private fun bindIntents(view: V) {
        currentViewIntentsDisposable = Observable
            .merge(view.provideViewIntents())
            .subscribe(
                currentViewIntentsSubject::onNext,
                currentViewIntentsSubject::onError
            )
    }

    /**
     * Subscribe to [cold observable][viewStateBehaviorSubject] of wrapped intents to [render][IBaseView.render]
     * the reduced [view state][IBaseViewState]
     *
     * This method should be called **every time** when view is attached.
     */
    private fun subscribeViewStateConsumer() {
        viewStateConsumerDisposable = viewStateBehaviorSubject
            .subscribeOn(viewStateSubscriptionScheduler)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(view::render)
    }

    /**
     * Subscribe to emitter of provided wrapped intents and [reduce][BaseMviPresenter.reduce]
     * the view state with each emission.
     *
     * This method should be called **once**. The best time to call it when view is attached for the first time.
     */
    private fun subscribeViewIntents() {
        viewStateReducerDisposable =
            currentViewIntentsSubject
                .flatMap(::mapViewIntentToPartialState)
                .mergeWith(providePresenterIntents())
                .scan(currentState, this::reduce)
                .distinctUntilChanged()
                .doOnNext { currentState = it }
                .subscribe(
                    viewStateBehaviorSubject::onNext,
                    viewStateBehaviorSubject::onError
                )
    }

    /**
     * Clears subscription of view state's reducer and view intents.
     *
     * Reset the presenter state but it rather should not be used again.
     */
    private fun reset() {

        disposeViewStateReducer()

        disposeCurrentViewIntents()

        isViewAttachedFirstTime = true
    }

    /**
     * Dispose view's consumer subscription.
     */
    private fun disposeViewStateConsumer() {
        viewStateConsumerDisposable?.let {
            it.dispose()
            viewStateConsumerDisposable = null
        }
    }

    /**
     * Dispose current view's intents.
     */
    private fun disposeCurrentViewIntents() {
        currentViewIntentsDisposable?.let {
            it.dispose()
            currentViewIntentsDisposable = null
        }
    }

    /**
     * Dispose subscription of all provided intents for view state's consumer.
     */
    private fun disposeViewStateReducer() {
        viewStateReducerDisposable?.let {
            it.dispose()
            viewStateReducerDisposable = null
        }
    }


}