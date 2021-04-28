package pl.valueadd.mvi.example.presentation.main.first

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import io.reactivex.Observable
import pl.valueadd.mvi.example.R
import pl.valueadd.mvi.example.databinding.FragmentFirstBinding
import pl.valueadd.mvi.example.presentation.base.AbstractBaseMviFragment
import pl.valueadd.mvi.example.utility.extension.onSuccess
import pl.valueadd.mvi.example.utility.extension.throttleClicks
import pl.valueadd.mvi.fragment.base.FragmentBindingInflater
import javax.inject.Inject

class FirstFragment :
    AbstractBaseMviFragment<FirstView, FirstViewState, FirstView.Intent, FirstPresenter, FragmentFirstBinding>(),
    FirstView {

    @Inject
    override lateinit var presenter: FirstPresenter

    override val bindingInflater: FragmentBindingInflater<FragmentFirstBinding>
        get() = FragmentFirstBinding::inflate

    private val buttonDelay
            by lazy { resources.getInteger(R.integer.button_delay_100).toLong() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeListeners()
    }

    override fun provideInitialViewState(): FirstViewState {
        return restoredViewState ?: FirstViewState()
    }

    override fun navigateToAboutView() {
        findNavController().navigate(R.id.action_firstFragment_to_aboutFragment)
    }

    override fun render(state: FirstViewState): Unit = with(requireBinding) {
        counterText.text = state.count.toString()
        resultValueText.text = state.value
    }

    override fun provideViewIntents(): List<Observable<FirstView.Intent>> = listOf(
        increaseCount(),
        decreaseCount(),
        processData()
    )

    private fun increaseCount(): Observable<FirstView.Intent> =
        requireBinding.addButton
            .throttleClicks(buttonDelay)
            .map { FirstView.Intent.IncreaseCount }

    private fun decreaseCount(): Observable<FirstView.Intent> =
        requireBinding.removeButton
            .throttleClicks(buttonDelay)
            .map { FirstView.Intent.DecreaseCount }

    private fun processData(): Observable<FirstView.Intent> =
        requireBinding.processButton
            .throttleClicks()
            .map { FirstView.Intent.ProcessData }

    private fun initializeListeners() {
        requireBinding.aboutButton
            .throttleClicks()
            .onSuccess(disposables, { navigateToAboutView() })
    }
}
