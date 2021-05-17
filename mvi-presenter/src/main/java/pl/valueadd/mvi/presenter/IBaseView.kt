package pl.valueadd.mvi.presenter

import io.reactivex.Observable
import io.reactivex.disposables.Disposable

interface IBaseView<VS : IBaseViewState, VI : IBaseView.IBaseIntent, VE : IBaseView.IBaseEffect> : Disposable {

    fun render(state: VS)

    fun handleViewEffect(effect: VE) {
        // No implementation required by default
    }

    /**
     * Example implementation:
     *
     * ```
     *     override fun provideViewIntents(): List<Observable<SampleView.Intent>> = listOf(
     *          doSomething(),
     *          doMoreThanSomething(),
     *          processSomething()
     *     )
     * ```
     * @see BaseMviPresenter.providePresenterIntents
     */
    fun provideViewIntents(): List<Observable<out VI>>

    fun provideInitialViewState(): VS

    interface IBaseIntent

    interface IBaseEffect
}
