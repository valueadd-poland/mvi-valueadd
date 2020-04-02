package pl.valueadd.mvi.example.presentation.main.about

import io.reactivex.Observable
import pl.valueadd.mvi.example.R
import pl.valueadd.mvi.example.presentation.base.AbstractBackMviFragment
import pl.valueadd.mvi.presenter.IBaseView
import javax.inject.Inject

class AboutFragment :
    AbstractBackMviFragment<AboutView, AboutViewState, IBaseView.IBaseIntent, AboutPresenter>(R.layout.fragment_about),
    AboutView {

    companion object {

        fun createInstance(): AboutFragment =
            AboutFragment()
    }

    @Inject
    override lateinit var presenter: AboutPresenter

    override val titleRes: Int =
        R.string.about_title

    override fun render(state: AboutViewState) {
        // no-op
    }

    override fun provideViewIntents(): List<Observable<IBaseView.IBaseIntent>> = listOf()

    override fun provideInitialViewState(): AboutViewState {
        return restoredViewState ?: AboutViewState()
    }
}
