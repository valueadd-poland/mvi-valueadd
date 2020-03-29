package pl.valueadd.mvi.presenter

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable

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
