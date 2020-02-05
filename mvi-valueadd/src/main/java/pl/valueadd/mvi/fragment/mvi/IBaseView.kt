package pl.valueadd.mvi.fragment.mvi

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

interface IBaseView<VS : IBaseViewState, VI: IBaseView.IBaseIntent> {

    val disposables: CompositeDisposable

    fun render(state: VS)

    fun provideViewIntents(): List<Observable<VI>>

    interface IBaseIntent
}
