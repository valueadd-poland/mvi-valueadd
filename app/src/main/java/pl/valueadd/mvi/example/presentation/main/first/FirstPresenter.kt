package pl.valueadd.mvi.example.presentation.main.first

import android.util.Log
import io.reactivex.Observable
import io.reactivex.Single
import org.apache.commons.lang3.StringUtils.EMPTY
import pl.valueadd.mvi.example.presentation.base.AbstractPresenter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class FirstPresenter @Inject constructor() : AbstractPresenter<FirstViewState, FirstViewState.PartialState, FirstView.Intent, FirstView>() {
    companion object {
        private const val PROCESING_DELAY = 5L
    }

    override fun mapViewIntentToPartialState(viewIntent: FirstView.Intent): Observable<out FirstViewState.PartialState> =
        when (viewIntent) {
            FirstView.Intent.IncreaseCount -> handleIncreaseCountIntent()
            FirstView.Intent.DecreaseCount -> handleDecreaseCountIntent()
            FirstView.Intent.ProcessData -> handleProcessDataIntent()
        }

    override fun reduce(
        previousState: FirstViewState,
        action: FirstViewState.PartialState
    ): FirstViewState = when (action) {
        is FirstViewState.PartialState.ChangeCountSuccess -> previousState.copy(count = action.count)
        is FirstViewState.PartialState.ChangeCountFail -> previousState.copy(error = action.error.localizedMessage ?: EMPTY)
        is FirstViewState.PartialState.ProcessDataSuccess -> previousState.copy(value = action.value)
        is FirstViewState.PartialState.ProcessDataFail -> previousState.copy(error = action.error.localizedMessage ?: EMPTY)
    }

    private fun handleIncreaseCountIntent(): Observable<out FirstViewState.PartialState> {
        return Single.just(currentState.count + 1)
            .map { FirstViewState.PartialState.ChangeCountSuccess(it) }
            .toObservable()
    }

    private fun handleDecreaseCountIntent(): Observable<out FirstViewState.PartialState> {
        return Single.just(currentState.count - 1)
            .map { FirstViewState.PartialState.ChangeCountSuccess(it) }
            .toObservable()
    }

    private fun handleProcessDataIntent(): Observable<out FirstViewState.PartialState> {
        return Observable
            .just(currentState.count)
            .doOnNext { Log.d("MVI-FirstPresenter", "processData") }
            .delay(PROCESING_DELAY, TimeUnit.SECONDS)
            .map { count -> FirstViewState.PartialState.ProcessDataSuccess("$count%") }
    }
}