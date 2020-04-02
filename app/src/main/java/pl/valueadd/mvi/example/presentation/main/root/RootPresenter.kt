package pl.valueadd.mvi.example.presentation.main.root

import io.reactivex.Observable
import pl.valueadd.mvi.example.presentation.base.AbstractPresenter
import pl.valueadd.mvi.presenter.IBaseView
import javax.inject.Inject

class RootPresenter
@Inject constructor() : AbstractPresenter<RootViewState, RootViewState.PartialState, IBaseView.IBaseIntent, RootView>() {

    override fun mapViewIntentToPartialState(viewIntent: IBaseView.IBaseIntent): Observable<out RootViewState.PartialState> =
        Observable.never()

    override fun reduce(previousState: RootViewState, action: RootViewState.PartialState): RootViewState =
        previousState
}