package pl.valueadd.mvi.presenter

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.UnicastSubject
import pl.valueadd.mvi.exception.ViewNotAttachedException
import pl.valueadd.mvi.exception.ViewWasNotDetachedException

abstract class BaseMviPresenter<
        VS : IBaseViewState,
        PS : IBasePartialState,
        VI : IBaseView.IBaseIntent,
        VE : IBaseView.IBaseEffect,
        V : IBaseView<VS, VI, VE>>(mainThread: Scheduler) : IMviPresenter<V> {

    //region Variables

    /**
     * Current view state.
     */
    lateinit var currentState: VS
        private set

    /**
     * Hot observable of [view state][IBaseViewState].
     */
    val stateObservable: Observable<VS>
        get() = viewStateBehaviorSubject.share()

    /**
     * Returns [view][IBaseView] but may throw [ViewNotAttachedException] if called in wrong place.
     * @throws ViewNotAttachedException if called in wrong place
     */
    @get:Throws(ViewNotAttachedException::class)
    protected val view: V
        get() = internalView ?: throw ViewNotAttachedException()

    /**
     * @see subscribeViewStateConsumer
     * @see viewStateConsumerDisposable
     */
    protected open val viewStateSubscriptionScheduler: Scheduler =
        Schedulers.io()

    /**
     * @see subscribeViewStateConsumer
     * @see viewStateConsumerDisposable
     */
    protected open val viewStateObservationScheduler: Scheduler =
        mainThread

    /**
     * Internal nullable [view][IBaseView] of presenter.
     */
    private var internalView: V? = null

    /**
     * Use to determine when a intents have to be bound.
     */
    private var wasViewAttachedOnce = false

    /**
     * A disposable container of temporarily bound view intents.
     */
    private var currentViewIntentsDisposable: Disposable? = null

    /**
     * A disposable of wrapped intents which use to reduce a [view state][IBaseViewState]
     */
    private var viewStateReducerDisposable: Disposable? = null

    /**
     * A disposable of currently bound consumer for emission of wrapped intents.
     */
    private var viewStateConsumerDisposable: Disposable? = null

    /**
     * A disposable of currently bound consumer for emission of view effects.
     */
    private var viewEffectConsumerDisposable: Disposable? = null

    /**
     * A subject to pass emission of wrapped intents to currently bound view's consumer.
     */
    private val viewStateBehaviorSubject: BehaviorSubject<VS> by lazy {
        BehaviorSubject.createDefault(currentState)
    }

    /**
     * A subject to pass emission of view effects to currently bound view's consumer.
     */
    private var viewEffectUnicastSubject = UnicastSubject.create<VE>()

    /**
     * A subject to wrap view's intents emission.
     */
    private val currentViewIntentsSubject =
        PublishSubject.create<VI>()

    //endregion

    //region Lifecycle methods

    /**
     * If view wasn't attached the first time, the presenter calls [IBaseView.provideInitialViewState]
     * to set initial value of [currentState]
     */
    override fun initializeState(view: V) {
        if (!wasViewAttachedOnce) {
            currentState = view.provideInitialViewState()
        }
    }

    /**
     * If view is attached the first time, the presenter subscribes its provided intents.
     * Each time subscribes to view state's consumer and bind view's intents.
     *
     * This should be called on fragment's **start**.
     *
     * @throws ViewWasNotDetachedException if previous view was not detached
     */
    @Throws(ViewWasNotDetachedException::class)
    override fun attachView(view: V) {
        if (this.internalView != null) {
            throw ViewWasNotDetachedException()
        }

        this.internalView = view

        if (!wasViewAttachedOnce) {
            startObservingCurrentViewStateSubject()
            wasViewAttachedOnce = true
        }

        subscribeViewStateConsumer()
        subscribeViewEffectConsumer()
        bindIntents(view)
    }

    /**
     * Dispose view's consumer subscription and view's intents.
     *
     * This should be called on fragment's **stop**.
     */
    override fun detachView() {
        this.internalView = null

        disposeViewStateConsumer()
        disposeViewEffectConsumer()

        disposeCurrentViewIntents()
    }

    /**
     * Dispose subscribed wrapped intents and current view's intents.
     *
     * This should be called on fragment's **destroy**.
     */
    override fun destroy() {
        reset()
    }

    //endregion

    /**
     * Map view's intent actions to [provided presenter intents][BaseMviPresenter.providePresenterIntents].
     *
     * Example implementation:
     *
     * ```
     *     override fun mapViewIntentToPartialState(viewIntent: SampleView.Intent): Observable<out SampleViewState.PartialState> =
     *          when (viewIntent) {
     *                  SampleView.Intent.DoSomething -> handleDoSomethingIntent()
     *                  SampleView.Intent.DoMoreThanSomething -> handleDoMoreThanSomethingIntent()
     *                  SampleView.Intent.ProcessSomething -> handleProcessSomethingIntent()
     *          }
     * ```
     * @see BaseMviPresenter.providePresenterIntents
     */
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
     * override fun providePresenterIntents(): List<Observable<out SampleViewState.PartialState>> = listOf(
     *      handleDoSomethingIntent(),
     *      handleDoMoreThanSomethingIntent(),
     *      handleProcessSomethingIntent()
     * )
     * ```
     * @see BaseMviPresenter.mapViewIntentToPartialState
     */
    protected open fun providePresenterIntents(): List<Observable<out PS>> = listOf()

    /**
     * Pushes view effect to view if attached or if detached, will emit them after view attach
     */
    protected fun pushViewEffect(effect: VE) {
        viewEffectUnicastSubject.onNext(effect)
    }

    /**
     * Method which is called when an Exception occurred during observing external
     * observables or reducing states.
     */
    protected open fun onError(throwable: Throwable) {
        // no-op
    }

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
     * Subscribe to [hot observable][viewStateBehaviorSubject] of wrapped intents to [render][IBaseView.render]
     * the reduced [view state][IBaseViewState]
     *
     * This method should be called **every time** when view is attached.
     */
    private fun subscribeViewStateConsumer() {
        viewStateConsumerDisposable = viewStateBehaviorSubject
            .subscribeOn(viewStateSubscriptionScheduler)
            .observeOn(viewStateObservationScheduler)
            .subscribe(view::render)
    }

    /**
     * Subscribe to [hot observable][viewEffectUnicastSubject] of view effets to handle [IBaseView.handleViewEffect]
     *
     * This method should be called **every time** when view is attached.
     */
    private fun subscribeViewEffectConsumer() {
        viewEffectConsumerDisposable = viewEffectUnicastSubject
            .subscribeOn(viewStateSubscriptionScheduler)
            .observeOn(viewStateObservationScheduler)
            .subscribe(view::handleViewEffect)
    }

    /**
     * Subscribe to emitter of provided wrapped intents and [reduce][BaseMviPresenter.reduce]
     * the view state with each emission.
     *
     * This method should be called **once**. The best time to call it when view is attached for the first time.
     */
    private fun startObservingCurrentViewStateSubject() {
        viewStateReducerDisposable =
            currentViewIntentsSubject
                .flatMap(::mapViewIntentToPartialState)
                .mergeWith(Observable.merge(providePresenterIntents()))
                .scan(currentState, this::reduce)
                .distinctUntilChanged()
                .doOnNext { currentState = it }
                .subscribe(
                    viewStateBehaviorSubject::onNext,
                    ::onError
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

        wasViewAttachedOnce = false
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
     * Dispose view's consumer subscription.
     */
    private fun disposeViewEffectConsumer() {
        viewEffectConsumerDisposable?.let {
            it.dispose()
            viewEffectConsumerDisposable = null
        }
        viewEffectUnicastSubject = UnicastSubject.create()
    }

    /**
     * Dispose current view's intents subscription.
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