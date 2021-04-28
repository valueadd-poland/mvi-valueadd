package pl.valueadd.mvi.example.presentation.main.about

import android.os.Bundle
import android.view.View
import io.reactivex.Observable
import pl.valueadd.mvi.example.R
import pl.valueadd.mvi.example.databinding.FragmentAboutBinding
import pl.valueadd.mvi.example.presentation.base.AbstractBaseMviFragment
import pl.valueadd.mvi.fragment.base.FragmentBindingInflater
import pl.valueadd.mvi.presenter.IBaseView
import javax.inject.Inject

class AboutFragment :
    AbstractBaseMviFragment<AboutView, AboutViewState, IBaseView.IBaseIntent, AboutPresenter, FragmentAboutBinding>(),
    AboutView {

    override val bindingInflater: FragmentBindingInflater<FragmentAboutBinding>
        get() = FragmentAboutBinding::inflate

    companion object {

        fun createInstance(): AboutFragment =
            AboutFragment()
    }

    @Inject
    override lateinit var presenter: AboutPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
    }

    private fun setupToolbar() = with(requireBinding.baseAppBarLayout.baseToolbar) {
        setTitle(R.string.about_title)
        setupWithNavController()
    }

    override fun render(state: AboutViewState) {
        // no-op
    }

    override fun provideViewIntents(): List<Observable<IBaseView.IBaseIntent>> = listOf()

    override fun provideInitialViewState(): AboutViewState {
        return restoredViewState ?: AboutViewState()
    }
}
