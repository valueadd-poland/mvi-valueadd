package pl.valueadd.mvi.example.presentation.main.first

import android.os.Bundle
import android.view.View
import pl.valueadd.mvi.example.presentation.main.about.AboutFragment
import pl.valueadd.mvi.example.presentation.main.root.RootFragment
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_first.*
import pl.valueadd.mvi.example.R
import pl.valueadd.mvi.example.presentation.base.AbstractBaseMviFragment
import pl.valueadd.mvi.example.utility.extension.onSuccess
import pl.valueadd.mvi.example.utility.extension.throttleClicks
import javax.inject.Inject

class FirstFragment :
    AbstractBaseMviFragment<FirstView, FirstViewState, FirstView.Intent, FirstPresenter>(R.layout.fragment_first),
    FirstView {

    companion object {
        fun createInstance(): FirstFragment =
            FirstFragment()
    }

    @Inject
    override lateinit var presenter: FirstPresenter

    private val buttonDelay
        by lazy { resources.getInteger(R.integer.button_delay_100).toLong() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeListeners()
    }

    override fun navigateToAboutView() {
        val fragment = AboutFragment.createInstance()

        getParentFragment(RootFragment::class.java)
            .start(fragment)
    }

    override fun render(state: FirstViewState) {
        counterText.text = state.count.toString()
        resultValueText.text = state.value
    }

    override fun provideViewIntents(): List<Observable<FirstView.Intent>> = listOf(
        increaseCount(),
        decreaseCount(),
        processData()
    )

    private fun increaseCount(): Observable<FirstView.Intent> =
        addButton
            .throttleClicks(buttonDelay)
            .map { FirstView.Intent.IncreaseCount }

    private fun decreaseCount(): Observable<FirstView.Intent> =
        removeButton
            .throttleClicks(buttonDelay)
            .map { FirstView.Intent.DecreaseCount }

    private fun processData(): Observable<FirstView.Intent> =
        processButton
            .throttleClicks()
            .map { FirstView.Intent.ProcessData }

    private fun initializeListeners() {
        aboutButton
            .throttleClicks()
            .onSuccess(disposables, { navigateToAboutView() })
    }

}