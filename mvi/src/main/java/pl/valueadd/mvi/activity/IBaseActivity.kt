package pl.valueadd.mvi.activity

import pl.valueadd.mvi.fragment.base.IBaseFragment
import me.yokeyword.fragmentation.ISupportActivity
import me.yokeyword.fragmentation.ISupportFragment

interface IBaseActivity : ISupportActivity {

    fun loadRootFragment(containerId: Int, toFragment: IBaseFragment)

    fun start(toFragment: IBaseFragment)

    fun start(toFragment: IBaseFragment, @ISupportFragment.LaunchMode launchMode: Int)

    fun startWithPopTo(
        toFragment: IBaseFragment,
        targetFragmentClass: Class<*>,
        includeTargetFragment: Boolean
    )

    fun pop()

    fun popTo(targetFragmentClass: Class<*>, includeTargetFragment: Boolean)

    fun popTo(
        targetFragmentClass: Class<*>,
        includeTargetFragment: Boolean,
        afterPopTransactionRunnable: Runnable
    )

    fun popTo(
        targetFragmentClass: Class<*>,
        includeTargetFragment: Boolean,
        afterPopTransactionRunnable: Runnable,
        popAnim: Int
    )

    fun <F : IBaseFragment> findFragment(fragmentClass: Class<F>): F?

    fun getTopFragment(): ISupportFragment

    /**
     * Returns top fragment as desired class.
     *
     * If fragment is not a given class this returns null.
     */
    fun <T : ISupportFragment> getTopFragment(fragmentClass: Class<T>): T?
}