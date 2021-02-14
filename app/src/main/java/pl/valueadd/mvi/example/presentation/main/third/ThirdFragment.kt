package pl.valueadd.mvi.example.presentation.main.third

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import io.reactivex.Observable
import pl.valueadd.mvi.example.R
import pl.valueadd.mvi.example.databinding.FragmentThirdBinding
import pl.valueadd.mvi.example.presentation.base.AbstractBaseMviFragment
import pl.valueadd.mvi.example.utility.extension.onSuccess
import pl.valueadd.mvi.example.utility.extension.throttleClicks
import pl.valueadd.mvi.fragment.base.FragmentBindingInflater
import pl.valueadd.mvi.presenter.IBaseView
import javax.inject.Inject

class ThirdFragment : AbstractBaseMviFragment<ThirdView, ThirdViewState, IBaseView.IBaseIntent, ThirdPresenter, FragmentThirdBinding>(),
    ThirdView {

    @Inject
    override lateinit var presenter: ThirdPresenter

    override val bindingInflater: FragmentBindingInflater<FragmentThirdBinding>
        get() = FragmentThirdBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindListeners()
    }

    fun navigateToAboutView() {
        findNavController().navigate(R.id.action_thirdFragment_to_aboutFragment)
    }

    private fun bindListeners() {
       requireBinding.aboutButton
            .throttleClicks()
            .onSuccess(disposables, { navigateToAboutView() })
    }

    override fun render(state: ThirdViewState) {
        // no-op
    }

    override fun provideViewIntents(): List<Observable<IBaseView.IBaseIntent>> = listOf()

    override fun provideInitialViewState(): ThirdViewState {
        return restoredViewState ?: ThirdViewState()
    }

    companion object {

        fun createInstance(): ThirdFragment =
            ThirdFragment()
    }
}