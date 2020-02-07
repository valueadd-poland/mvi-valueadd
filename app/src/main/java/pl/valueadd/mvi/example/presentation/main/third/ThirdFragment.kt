package pl.valueadd.mvi.example.presentation.main.third

import android.os.Bundle
import android.view.View
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_third.aboutButton
import pl.valueadd.mvi.example.R
import pl.valueadd.mvi.example.presentation.base.AbstractBaseMviFragment
import pl.valueadd.mvi.example.presentation.main.about.AboutFragment
import pl.valueadd.mvi.example.presentation.main.root.RootFragment
import pl.valueadd.mvi.example.utility.extension.onSuccess
import pl.valueadd.mvi.example.utility.extension.throttleClicks
import pl.valueadd.mvi.fragment.mvi.IBaseView
import javax.inject.Inject

class ThirdFragment : AbstractBaseMviFragment<ThirdView, ThirdViewState, IBaseView.IBaseIntent, ThirdPresenter>(
    R.layout.fragment_third),
    ThirdView {

    companion object {

        fun createInstance(): ThirdFragment =
            ThirdFragment()
    }

    @Inject
    override lateinit var presenter: ThirdPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindListeners()
    }

    fun navigateToAboutView() {
        val fragment = AboutFragment.createInstance()

        getParentFragment(RootFragment::class.java)
            .start(fragment)
    }

    private fun bindListeners() {
        aboutButton
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
}