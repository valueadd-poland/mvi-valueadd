package pl.valueadd.mvi.fragment.base

import android.view.View
import pl.valueadd.mvi.activity.IBaseActivity
import me.yokeyword.fragmentation.ISupportFragment

interface IBaseFragment : ISupportFragment {

    val isBackStackAtRoot: Boolean

    fun getBaseActivity(): IBaseActivity

    fun hideSoftInput()

    fun showSoftInput(view: View)

    fun loadRootFragment(containerId: Int, toFragment: IBaseFragment)

    fun loadRootFragment(
        containerId: Int,
        toFragment: IBaseFragment,
        addToBackStack: Boolean,
        allowAnimation: Boolean
    )

    fun loadMultipleRootFragment(
        containerId: Int,
        showPosition: Int,
        vararg toFragments: IBaseFragment
    )

    fun start(toFragment: IBaseFragment)

    fun start(toFragment: IBaseFragment, @ISupportFragment.LaunchMode launchMode: Int)

    fun startForResult(toFragment: IBaseFragment, requestCode: Int)

    fun startWithPop(toFragment: IBaseFragment)

    fun startWithPopTo(
        toFragment: IBaseFragment,
        targetFragmentClass: Class<*>,
        includeTargetFragment: Boolean
    )

    fun replaceFragment(toFragment: IBaseFragment, addToBackStack: Boolean)

    fun showHideFragment(showFragment: IBaseFragment)

    fun showHideFragment(showFragment: IBaseFragment, hideFragment: IBaseFragment)

    fun pop()

    fun popTo(targetFragmentClass: Class<*>, includeTargetFragment: Boolean)

    fun <T : IBaseFragment> findChildFragment(fragmentClass: Class<T>): T?

    /**
     * Calls on host [BaseMVPActivity][pl.valueadd.mvi.activity.BaseActivity] a
     * [findFragment(Class)][pl.valueadd.mvi.activity.BaseActivity.findFragment]
     * method.
     */
    fun <T : IBaseFragment> findFragment(fragmentClass: Class<T>): T?

    /**
     * Return parent fragment.
     * @throws IllegalArgumentException when given class does not belong to back stack.
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : IBaseFragment> getParentFragment(fragmentClass: Class<T>): T

    /**
     * Return activity of the fragment.
     * @throws IllegalArgumentException when given class does not host current fragment.
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : IBaseActivity> getActivity(activityClass: Class<T>): T
}