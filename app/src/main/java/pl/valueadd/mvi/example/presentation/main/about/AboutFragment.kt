package pl.valueadd.mvi.example.presentation.main.about

import androidx.appcompat.widget.Toolbar
import io.reactivex.Observable
import pl.valueadd.mvi.example.R
import pl.valueadd.mvi.example.databinding.FragmentAboutBinding
import pl.valueadd.mvi.example.presentation.base.AbstractBackMviFragment
import pl.valueadd.mvi.fragment.base.FragmentBindingInflater
import pl.valueadd.mvi.presenter.IBaseView
import javax.inject.Inject

class AboutFragment :
    AbstractBackMviFragment<AboutView, AboutViewState, IBaseView.IBaseIntent, AboutPresenter, FragmentAboutBinding>(),
    AboutView {

    override val navigationIcon: Int
        get() = R.drawable.ic_arrow_back_white_24dp

    override val toolbarNavigation: Toolbar
        get() = requireBinding.baseAppBarLayout.baseToolbar

    override val bindingInflater: FragmentBindingInflater<FragmentAboutBinding>
        get() = FragmentAboutBinding::inflate

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
