package pl.valueadd.mvi.example.presentation.main.second

import io.reactivex.Observable
import org.apache.commons.lang3.StringUtils.EMPTY
import pl.valueadd.mvi.example.presentation.base.AbstractPresenter
import pl.valueadd.mvi.presenter.IBaseView
import javax.inject.Inject

class SecondPresenter
@Inject constructor() : AbstractPresenter<SecondViewState, SecondViewState.PartialState, IBaseView.IBaseIntent, SecondView>() {

    override fun reduce(
        previousState: SecondViewState,
        action: SecondViewState.PartialState
    ): SecondViewState = when (action) {
        is SecondViewState.PartialState.LoadExamplesSuccess -> previousState.copy(list = action.list)
        is SecondViewState.PartialState.LoadExamplesFail -> previousState.copy(error = action.error.localizedMessage ?: EMPTY)
    }

    override fun mapViewIntentToPartialState(viewIntent: IBaseView.IBaseIntent): Observable<out SecondViewState.PartialState> = Observable.never()
}