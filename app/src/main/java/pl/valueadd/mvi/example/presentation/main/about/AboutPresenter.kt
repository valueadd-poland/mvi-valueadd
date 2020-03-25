package pl.valueadd.mvi.example.presentation.main.about

import io.reactivex.Observable
import pl.valueadd.mvi.example.presentation.base.AbstractPresenter
import pl.valueadd.mvi.presenter.IBaseView
import javax.inject.Inject

class AboutPresenter @Inject constructor() :
    AbstractPresenter<AboutViewState, AboutViewState.PartialState, IBaseView.IBaseIntent, AboutView>() {

    override fun reduce(
        previousState: AboutViewState,
        action: AboutViewState.PartialState
    ): AboutViewState = previousState

    override fun mapViewIntentToPartialState(viewIntent: IBaseView.IBaseIntent): Observable<out AboutViewState.PartialState> =
        Observable.never()
}