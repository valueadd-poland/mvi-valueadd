package pl.valueadd.mvi.fragment.back

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.base_toolbar.*
import pl.valueadd.mvi.fragment.back.delegation.BackFragmentDelegate
import pl.valueadd.mvi.fragment.back.delegation.BackFragmentDelegateImpl
import pl.valueadd.mvi.fragment.base.BaseFragment
import me.yokeyword.fragmentation.anim.FragmentAnimator

abstract class BackFragment(@LayoutRes layoutId: Int) : BaseFragment(layoutId),
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