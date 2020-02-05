package pl.valueadd.mvi.example.presentation.main.account

import pl.valueadd.mvi.fragment.mvi.IBaseView

interface AccountView :
    IBaseView<AccountViewState, AccountView.Intent> {

    sealed class Intent : IBaseView.IBaseIntent {
        class FirstNameChanged(val newValue: String) : Intent()
        class LastNameChanged(val newValue: String) : Intent()
        class EmailChanged(val newValue: String) : Intent()
    }
}