package pl.valueadd.mvi.example.presentation.main.account

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.fragment_account.emailText
import kotlinx.android.synthetic.main.fragment_account.firstNameText
import kotlinx.android.synthetic.main.fragment_account.surnameText
import pl.valueadd.mvi.example.R
import pl.valueadd.mvi.example.presentation.base.AbstractBackMviFragment
import pl.valueadd.mvi.example.utility.extension.applyTextChanges
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


    private val destroyViewIntent: Subject<AccountView.Intent>
        by lazy { PublishSubject.create<AccountView.Intent>() }

    override fun render(state: AccountViewState) {
        firstNameText.applyTextChanges(state.firstName)
        surnameText.applyTextChanges(state.surname)
        emailText.applyTextChanges(state.email)
    }

    override fun provideViewIntents(): List<Observable<AccountView.Intent>> = listOf(
        destroyViewIntent
    )

    override fun onStop() {
        destroyViewIntent.onNext(AccountView.Intent.OnDestroyView(
            firstNameText.text.toString(),
            surnameText.text.toString(),
            emailText.text.toString()
        ))
        super.onStop()
    }

}