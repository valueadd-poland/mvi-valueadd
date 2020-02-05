package pl.valueadd.mvi.example.presentation.main.second

import io.reactivex.Observable
import org.apache.commons.lang3.StringUtils.EMPTY
import pl.valueadd.mvi.fragment.mvi.BaseMviPresenter
import pl.valueadd.mvi.fragment.mvi.IBaseView
import javax.inject.Inject

class SecondPresenter
@Inject constructor(
) : BaseMviPresenter<SecondViewState, SecondViewState.PartialState, IBaseView.IBaseIntent, SecondView>(
    SecondViewState()
) {

    override fun reduce(
        previousState: SecondViewState,
        action: SecondViewState.PartialState
    ): SecondViewState = when (action) {
        is SecondViewState.PartialState.LoadExamplesSuccess -> previousState.copy(list = action.list)
        is SecondViewState.PartialState.LoadExamplesFail -> previousState.copy(error = action.error.localizedMessage ?: EMPTY)
    }

    override fun mapViewIntentToPartialState(viewIntent: IBaseView.IBaseIntent): Observable<out SecondViewState.PartialState> = Observable.never()

}