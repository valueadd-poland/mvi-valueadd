package pl.valueadd.mvi.example.presentation.main.account

import io.reactivex.Observable
import pl.valueadd.mvi.example.presentation.base.AbstractPresenter
import pl.valueadd.mvi.presenter.IBaseView
import javax.inject.Inject

class AccountPresenter @Inject constructor() :
    AbstractPresenter<AccountViewState, AccountViewState.PartialState, AccountView.Intent, IBaseView.IBaseEffect, AccountView>() {

    override fun reduce(
        previousState: AccountViewState,
        action: AccountViewState.PartialState
    ): AccountViewState = when (action) {
        is AccountViewState.PartialState.OnViewDestroyed ->
            previousState.copy(
                firstName = action.firstName,
                surname = action.surname,
                email = action.email
            )
    }

    override fun mapViewIntentToPartialState(viewIntent: AccountView.Intent): Observable<out AccountViewState.PartialState> =
        when (viewIntent) {
            is AccountView.Intent.OnDestroyView ->
                Observable.just(AccountViewState.PartialState.OnViewDestroyed(
                    firstName = viewIntent.firstName,
                    surname = viewIntent.surname,
                    email = viewIntent.email
                ))
        }
}