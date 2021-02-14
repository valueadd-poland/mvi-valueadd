package pl.valueadd.mvi.example.presentation.main.account

import androidx.appcompat.widget.Toolbar
import io.reactivex.Observable
import pl.valueadd.mvi.example.R
import pl.valueadd.mvi.example.databinding.FragmentAccountBinding
import pl.valueadd.mvi.example.presentation.base.AbstractBackMviFragment
import pl.valueadd.mvi.example.utility.extension.applyTextChanges
import pl.valueadd.mvi.fragment.base.FragmentBindingInflater
import pl.valueadd.mvi.fragment.delegate.destroyview.DestroyViewIntentDelegate
import javax.inject.Inject

class AccountFragment :
    AbstractBackMviFragment<AccountView, AccountViewState, AccountView.Intent, AccountPresenter, FragmentAccountBinding>(),
    AccountView {

    @Inject
    override lateinit var presenter: AccountPresenter

    override val navigationIcon: Int
        get() = R.drawable.ic_arrow_back_white_24dp

    override val toolbarNavigation: Toolbar
        get() = requireBinding.baseAppBarLayout.baseToolbar

    override val bindingInflater: FragmentBindingInflater<FragmentAccountBinding>
        get() = FragmentAccountBinding::inflate

    override val titleRes: Int =
        R.string.account_title

    private val destroyViewIntent
            by DestroyViewIntentDelegate(this, ::provideDestroyViewIntent)

    override fun render(state: AccountViewState): Unit = with(requireBinding) {
        firstNameText.applyTextChanges(state.firstName)
        surnameText.applyTextChanges(state.surname)
        emailText.applyTextChanges(state.email)
    }

    override fun provideViewIntents(): List<Observable<AccountView.Intent>> = listOf(
        destroyViewIntent
    )

    override fun provideInitialViewState(): AccountViewState {
        return restoredViewState ?: AccountViewState()
    }

    private fun provideDestroyViewIntent(): AccountView.Intent = requireBinding.run {
        AccountView.Intent.OnDestroyView(
            firstNameText.text.toString(),
            surnameText.text.toString(),
            emailText.text.toString()
        )
    }

    companion object {

        fun createInstance(): AccountFragment =
            AccountFragment()
    }
}