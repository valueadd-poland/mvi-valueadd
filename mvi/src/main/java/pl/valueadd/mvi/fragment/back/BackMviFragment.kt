package pl.valueadd.mvi.fragment.back

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.base_toolbar.*
import pl.valueadd.mvi.fragment.back.delegate.BackFragmentDelegate
import pl.valueadd.mvi.fragment.back.delegate.BackFragmentDelegateImpl
import pl.valueadd.mvi.fragment.base.BaseMviFragment
import pl.valueadd.mvi.presenter.BaseMviPresenter
import pl.valueadd.mvi.presenter.IBaseView
import pl.valueadd.mvi.presenter.IBaseView.IBaseIntent
import pl.valueadd.mvi.presenter.IBaseViewState
import me.yokeyword.fragmentation.anim.FragmentAnimator

abstract class BackMviFragment<V : IBaseView<VS, *>, VS : IBaseViewState, VI : IBaseIntent, P : BaseMviPresenter<VS, *, *, V>>(@LayoutRes layoutId: Int) :
    BaseMviFragment<V, VS, VI, P>(layoutId),
    IBackFragment {

    private val backDelegate: BackFragmentDelegate
        by lazy { BackFragmentDelegateImpl(this, this) }

    override val toolbarNavigation: Toolbar
        get() = baseToolbar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backDelegate.onViewCreated(view, savedInstanceState)
    }

    override fun setTitle(@StringRes resId: Int) =
        backDelegate.setTitle(resId)

    override fun setTitle(title: String) =
        backDelegate.setTitle(title)

    override fun initializeToolbarNavigation(toolbar: Toolbar) =
        backDelegate.initializeToolbarNavigation(toolbar)

    override fun initializeToolbarMenu(toolbar: Toolbar) =
        backDelegate.initializeToolbarMenu(toolbar)

    override fun onCreateFragmentAnimator(): FragmentAnimator =
        backDelegate.onCreateFragmentAnimator()
}