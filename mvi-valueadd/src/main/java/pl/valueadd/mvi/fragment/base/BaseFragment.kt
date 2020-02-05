package pl.valueadd.mvi.fragment.base

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import me.yokeyword.fragmentation.ExtraTransaction
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.SupportFragmentDelegate
import me.yokeyword.fragmentation.SupportHelper
import me.yokeyword.fragmentation.anim.FragmentAnimator
import pl.valueadd.mvi.activity.IBaseActivity

abstract class BaseFragment(@LayoutRes protected val layoutId: Int) : Fragment(), IBaseFragment {

    private val fragmentDelegate: SupportFragmentDelegate
        by lazy { SupportFragmentDelegate(this) }

    /* Life cycle */

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(layoutId, container, false)

    @Suppress("DEPRECATION")
    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        fragmentDelegate.onAttach(activity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragmentDelegate.onCreate(savedInstanceState)

        retainInstance = true
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? =
        fragmentDelegate.onCreateAnimation(transit, enter, nextAnim)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fragmentDelegate.onActivityCreated(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        fragmentDelegate.onSaveInstanceState(outState)
    }

    override fun onResume() {
        super.onResume()
        fragmentDelegate.onResume()
    }

    override fun onPause() {
        super.onPause()
        fragmentDelegate.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentDelegate.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentDelegate.onDestroyView()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        fragmentDelegate.onHiddenChanged(hidden)
    }

    @Suppress("DEPRECATION")
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        fragmentDelegate.setUserVisibleHint(isVisibleToUser)
    }

    /* ISupportFragment */

    override fun setFragmentResult(resultCode: Int, bundle: Bundle?) =
        fragmentDelegate.setFragmentResult(resultCode, bundle)

    override fun onSupportInvisible() =
        fragmentDelegate.onSupportInvisible()

    override fun onNewBundle(args: Bundle?) =
        fragmentDelegate.onNewBundle(args)

    override fun extraTransaction(): ExtraTransaction =
        fragmentDelegate.extraTransaction()

    override fun onCreateFragmentAnimator(): FragmentAnimator =
        fragmentDelegate.onCreateFragmentAnimator()

    @Suppress("DEPRECATION")
    override fun enqueueAction(runnable: Runnable?) =
        fragmentDelegate.enqueueAction(runnable)

    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle?) =
        fragmentDelegate.onFragmentResult(requestCode, resultCode, data)

    override fun setFragmentAnimator(fragmentAnimator: FragmentAnimator?) {
        fragmentDelegate.fragmentAnimator = fragmentAnimator
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) =
        fragmentDelegate.onLazyInitView(savedInstanceState)

    override fun getFragmentAnimator(): FragmentAnimator? =
        fragmentDelegate.fragmentAnimator

    override fun isSupportVisible(): Boolean =
        fragmentDelegate.isSupportVisible

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) =
        fragmentDelegate.onEnterAnimationEnd(savedInstanceState)

    override fun onSupportVisible() =
        fragmentDelegate.onSupportVisible()

    override fun onBackPressedSupport(): Boolean =
        fragmentDelegate.onBackPressedSupport()

    override fun getSupportDelegate(): SupportFragmentDelegate =
        fragmentDelegate

    override fun putNewBundle(newBundle: Bundle?) =
        fragmentDelegate.putNewBundle(newBundle)

    override fun post(runnable: Runnable?) =
        fragmentDelegate.post(runnable)

    /* IBaseFragment */

    override val isBackStackAtRoot: Boolean
        get() = childFragmentManager.backStackEntryCount == 0

    override fun getBaseActivity(): IBaseActivity =
        activity as IBaseActivity

    override fun hideSoftInput() =
        fragmentDelegate.hideSoftInput()

    override fun showSoftInput(view: View) =
        fragmentDelegate.showSoftInput(view)

    override fun loadRootFragment(containerId: Int, toFragment: IBaseFragment) {
        fragmentDelegate.loadRootFragment(containerId, toFragment)
    }

    override fun loadRootFragment(
        containerId: Int,
        toFragment: IBaseFragment,
        addToBackStack: Boolean,
        allowAnimation: Boolean
    ) {
        fragmentDelegate.loadRootFragment(containerId, toFragment, addToBackStack, allowAnimation)
    }

    override fun loadMultipleRootFragment(
        containerId: Int,
        showPosition: Int,
        vararg toFragments: IBaseFragment
    ) {
        fragmentDelegate.loadMultipleRootFragment(containerId, showPosition, *toFragments)
    }

    override fun start(toFragment: IBaseFragment) {
        fragmentDelegate.start(toFragment)
    }

    override fun start(toFragment: IBaseFragment, @ISupportFragment.LaunchMode launchMode: Int) {
        fragmentDelegate.start(toFragment, launchMode)
    }

    override fun startForResult(toFragment: IBaseFragment, requestCode: Int) {
        fragmentDelegate.startForResult(toFragment, requestCode)
    }

    override fun startWithPop(toFragment: IBaseFragment) {
        fragmentDelegate.startWithPop(toFragment)
    }

    override fun startWithPopTo(
        toFragment: IBaseFragment,
        targetFragmentClass: Class<*>,
        includeTargetFragment: Boolean
    ) {
        fragmentDelegate.startWithPopTo(toFragment, targetFragmentClass, includeTargetFragment)
    }

    override fun replaceFragment(toFragment: IBaseFragment, addToBackStack: Boolean) {
        fragmentDelegate.replaceFragment(toFragment, addToBackStack)
    }

    override fun showHideFragment(showFragment: IBaseFragment) {
        fragmentDelegate.showHideFragment(showFragment)
    }

    override fun showHideFragment(
        showFragment: IBaseFragment,
        hideFragment: IBaseFragment
    ) {
        fragmentDelegate.showHideFragment(showFragment, hideFragment)
    }

    override fun pop() {
        fragmentDelegate.pop()
    }

    override fun popTo(targetFragmentClass: Class<*>, includeTargetFragment: Boolean) {
        fragmentDelegate.popTo(targetFragmentClass, includeTargetFragment)
    }

    override fun <T : IBaseFragment> findChildFragment(fragmentClass: Class<T>): T? =
        SupportHelper.findFragment(childFragmentManager, fragmentClass)

    override fun <T : IBaseFragment> findFragment(fragmentClass: Class<T>): T? =
        getBaseActivity().findFragment(fragmentClass)

    @Suppress("UNCHECKED_CAST")
    override fun <T : IBaseFragment> getParentFragment(fragmentClass: Class<T>): T {
        if (parentFragment == null || parentFragment?.javaClass != fragmentClass) {
            throw IllegalArgumentException("Parent fragment at given class does not belong to back stack.")
        }
        return parentFragment as T
    }

    /**
     * Return activity of the fragment.
     * @throws IllegalArgumentException when given class does not host current fragment.
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : IBaseActivity> getActivity(activityClass: Class<T>): T {
        if (activity == null || activity?.javaClass != activityClass) {
            throw IllegalArgumentException("Activity at given class does not host current fragment.")
        }
        return activity as T
    }
}