package pl.valueadd.mvi.fragment.back.delegate

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import pl.valueadd.mvi.R

interface BackFragmentDelegateCallback {

    @get:StringRes
    val titleRes: Int
        get() = R.string.common_empty

    @get:DrawableRes
    val navigationIcon: Int

    val toolbarNavigation: Toolbar

    fun setTitle(@StringRes resId: Int)

    fun setTitle(title: String)

    fun initializeToolbarNavigation(toolbar: Toolbar)

    fun initializeToolbarMenu(toolbar: Toolbar)
}