package pl.valueadd.mvi.fragment.back.delegation

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
        get() = R.drawable.ic_arrow_back_white_24dp

    val toolbarNavigation: Toolbar

    fun setTitle(@StringRes resId: Int)

    fun setTitle(title: String)

    fun initializeToolbarNavigation(toolbar: Toolbar)

    fun initializeToolbarMenu(toolbar: Toolbar)
}