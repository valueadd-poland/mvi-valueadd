package pl.valueadd.mvi.fragment.back

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
import pl.valueadd.mvi.fragment.back.delegate.BackFragmentDelegate
import pl.valueadd.mvi.fragment.back.delegate.BackFragmentDelegateImpl
import pl.valueadd.mvi.fragment.base.BaseFragment

abstract class BackFragment<Binding : ViewBinding> : BaseFragment<Binding>(),
    IBackFragment {

    private val backDelegate: BackFragmentDelegate
        by lazy { BackFragmentDelegateImpl(this, this) }

    abstract override val toolbarNavigation: Toolbar

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
}