package pl.valueadd.mvi.example.presentation.main.account

import android.os.Bundle
import android.view.View
import io.reactivex.Observable
import pl.valueadd.mvi.example.R
import pl.valueadd.mvi.example.databinding.FragmentAccountBinding
import pl.valueadd.mvi.example.presentation.base.AbstractBaseMviFragment
import pl.valueadd.mvi.example.utility.extension.applyTextChanges
import pl.valueadd.mvi.fragment.base.FragmentBindingInflater
import pl.valueadd.mvi.fragment.delegate.destroyview.DestroyViewIntentDelegate
import javax.inject.Inject

class AccountFragment :
    AbstractBaseMviFragment<AccountView, AccountViewState, AccountView.Intent, AccountPresenter, FragmentAccountBinding>(),
    AccountView {

    @Inject
    override lateinit var presenter: AccountPresenter

    override val bindingInflater: FragmentBindingInflater<FragmentAccountBinding>
        get() = FragmentAccountBinding::inflate

    private val destroyViewIntent
            by DestroyViewIntentDelegate(this, ::provideDestroyViewIntent)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
    }

    private fun setupToolbar() = with(requireBinding.baseAppBarLayout.baseToolbar) {
        setTitle(R.string.account_title)
        setupWithNavController()
    }

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
}