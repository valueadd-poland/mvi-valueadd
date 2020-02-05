package pl.valueadd.mvi.example.presentation.main.account

import io.reactivex.Observable
import pl.valueadd.mvi.fragment.mvi.BaseMviPresenter
import javax.inject.Inject

class AccountPresenter @Inject constructor() :
    BaseMviPresenter<AccountViewState, AccountViewState.PartialState, AccountView.Intent, AccountView>(
        AccountViewState()
    ) {

    override fun reduce(
        previousState: AccountViewState,
        action: AccountViewState.PartialState
    ): AccountViewState = when (action) {
        is AccountViewState.PartialState.FirstNameChanged -> previousState.copy(firstName = action.text)
        is AccountViewState.PartialState.LastNameChanged -> previousState.copy(surname = action.text)
        is AccountViewState.PartialState.EmailChanged -> previousState.copy(email = action.text)
    }

    override fun mapViewIntentToPartialState(viewIntent: AccountView.Intent): Observable<out AccountViewState.PartialState> =
        when (viewIntent) {
            is AccountView.Intent.FirstNameChanged ->
                Observable.just(AccountViewState.PartialState.FirstNameChanged(viewIntent.newValue))
            is AccountView.Intent.LastNameChanged ->
                Observable.just(AccountViewState.PartialState.LastNameChanged(viewIntent.newValue))
            is AccountView.Intent.EmailChanged ->
                Observable.just(AccountViewState.PartialState.EmailChanged(viewIntent.newValue))
        }
}