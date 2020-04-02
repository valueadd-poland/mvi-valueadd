package pl.valueadd.mvi.presenter

import io.reactivex.Observable
import io.reactivex.disposables.Disposable

interface IBaseView<VS : IBaseViewState, VI : IBaseView.IBaseIntent> : Disposable {

    fun render(state: VS)

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
}
