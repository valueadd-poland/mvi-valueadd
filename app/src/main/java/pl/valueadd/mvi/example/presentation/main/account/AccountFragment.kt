package pl.valueadd.mvi.example.presentation.main.account

import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_account.emailText
import kotlinx.android.synthetic.main.fragment_account.firstNameText
import kotlinx.android.synthetic.main.fragment_account.surnameText
import pl.valueadd.mvi.example.R
import pl.valueadd.mvi.example.presentation.base.AbstractBackMviFragment
import pl.valueadd.mvi.example.utility.extension.applyTextChanges
import pl.valueadd.mvi.example.utility.extension.throttleTextChanges
import javax.inject.Inject

class AccountFragment :
    AbstractBackMviFragment<AccountView, AccountViewState, AccountView.Intent, AccountPresenter>(R.layout.fragment_account),
    AccountView {

    companion object {

        fun createInstance(): AccountFragment =
            AccountFragment()
    }

    @Inject
    override lateinit var presenter: AccountPresenter

    override val titleRes: Int =
        R.string.account_title

    override fun render(state: AccountViewState) {
        firstNameText.applyTextChanges(state.firstName)
        surnameText.applyTextChanges(state.surname)
        emailText.applyTextChanges(state.email)
    }

    override fun provideViewIntents(): List<Observable<AccountView.Intent>> = listOf(
        typeFirstName(),
        typeLastName(),
        typeEmail()
    )

    private fun typeFirstName(): Observable<AccountView.Intent> =
        firstNameText
            .throttleTextChanges()
            .map { AccountView.Intent.FirstNameChanged(it.toString()) }

    private fun typeLastName(): Observable<AccountView.Intent> =
        surnameText
            .throttleTextChanges()
            .map { AccountView.Intent.LastNameChanged(it.toString()) }

    private fun typeEmail(): Observable<AccountView.Intent> =
        emailText
            .throttleTextChanges()
            .map { AccountView.Intent.EmailChanged(it.toString()) }
}