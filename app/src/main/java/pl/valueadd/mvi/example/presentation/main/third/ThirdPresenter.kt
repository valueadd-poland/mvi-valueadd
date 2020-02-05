package pl.valueadd.mvi.example.presentation.main.third

import io.reactivex.Observable
import pl.valueadd.mvi.fragment.mvi.BaseMviPresenter
import pl.valueadd.mvi.fragment.mvi.IBaseView
import javax.inject.Inject

class ThirdPresenter @Inject constructor() :
    BaseMviPresenter<ThirdViewState, ThirdViewState.PartialState, IBaseView.IBaseIntent, ThirdView>(
        ThirdViewState()
    ) {

    override fun reduce(
        previousState: ThirdViewState,
        action: ThirdViewState.PartialState
    ): ThirdViewState = previousState

    override fun mapViewIntentToPartialState(viewIntent: IBaseView.IBaseIntent): Observable<out ThirdViewState.PartialState> = Observable.never()
}