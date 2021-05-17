package pl.valueadd.mvi.example.presentation.main.third

import io.reactivex.Observable
import pl.valueadd.mvi.example.presentation.base.AbstractPresenter
import pl.valueadd.mvi.presenter.IBaseView
import javax.inject.Inject

class ThirdPresenter @Inject constructor() :
    AbstractPresenter<ThirdViewState, ThirdViewState.PartialState, IBaseView.IBaseIntent, IBaseView.IBaseEffect, ThirdView>() {

    override fun reduce(
        previousState: ThirdViewState,
        action: ThirdViewState.PartialState
    ): ThirdViewState = previousState

    override fun mapViewIntentToPartialState(viewIntent: IBaseView.IBaseIntent): Observable<out ThirdViewState.PartialState> = Observable.never()
}