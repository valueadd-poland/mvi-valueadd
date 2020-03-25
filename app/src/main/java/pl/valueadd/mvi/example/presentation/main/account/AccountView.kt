package pl.valueadd.mvi.example.presentation.main.account

import pl.valueadd.mvi.presenter.IBaseView

interface AccountView :
    IBaseView<AccountViewState, AccountView.Intent> {

    sealed class Intent : IBaseView.IBaseIntent {
        data class OnDestroyView(
            val firstName: String,
            val surname: String,
            val email: String
        ) : Intent()
    }
}