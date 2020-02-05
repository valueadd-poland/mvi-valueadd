package pl.valueadd.mvi.example.presentation.main.root

import io.reactivex.Observable
import pl.valueadd.mvi.fragment.mvi.BaseMviPresenter
import pl.valueadd.mvi.fragment.mvi.IBaseView
import javax.inject.Inject

class RootPresenter
@Inject constructor() : BaseMviPresenter<RootViewState, RootViewState.PartialState, IBaseView.IBaseIntent, RootView>(
    RootViewState()
) {

    override fun mapViewIntentToPartialState(viewIntent: IBaseView.IBaseIntent): Observable<out RootViewState.PartialState> =
        Observable.never()

    override fun reduce(previousState: RootViewState, action: RootViewState.PartialState): RootViewState =
        previousState
}