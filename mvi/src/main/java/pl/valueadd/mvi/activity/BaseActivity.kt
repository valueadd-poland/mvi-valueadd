package pl.valueadd.mvi.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import androidx.appcompat.app.AppCompatActivity
import pl.valueadd.mvi.fragment.base.IBaseFragment
import me.yokeyword.fragmentation.ExtraTransaction
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.SupportActivityDelegate
import me.yokeyword.fragmentation.SupportHelper
import me.yokeyword.fragmentation.anim.FragmentAnimator
import pl.valueadd.mvi.R

abstract class BaseActivity(@LayoutRes protected val layoutResourceId: Int = R.layout.base_activity_layout) :
    AppCompatActivity(),
    IBaseActivity {

    private val delegate: SupportActivityDelegate
        by lazy { SupportActivityDelegate(this) }

    @get:IdRes
    protected open val fragmentContainer: Int
        get() = R.id.fragmentContainer

    @get:MenuRes
    protected open val menuLayout: Int
        get() = R.menu.base_menu

    /* Life cycle methods */

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        delegate.onCreate(savedInstanceState)

        setContentView(layoutResourceId)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        delegate.onPostCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        delegate.onDestroy()
    }

    override fun onBackPressed() {
        // Do not invoke super method.
        delegate.onBackPressed()
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(menuItem)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(menuLayout, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean =
        delegate.dispatchTouchEvent(ev) || super.dispatchTouchEvent(ev)

    /* ISupportActivity */

    override fun setFragmentAnimator(fragmentAnimator: FragmentAnimator?) {
        delegate.fragmentAnimator = fragmentAnimator
    }

    override fun getFragmentAnimator(): FragmentAnimator =
        delegate.fragmentAnimator

    override fun onBackPressedSupport(): Unit =
        delegate.onBackPressedSupport()

    override fun extraTransaction(): ExtraTransaction =
        delegate.extraTransaction()

    override fun onCreateFragmentAnimator(): FragmentAnimator =
        delegate.onCreateFragmentAnimator()

    override fun getSupportDelegate(): SupportActivityDelegate =
        delegate

    override fun post(runnable: Runnable?): Unit =
        delegate.post(runnable)

    /* IBaseActivity */

    override fun loadRootFragment(containerId: Int, toFragment: IBaseFragment) {
        delegate.loadRootFragment(containerId, toFragment)
    }

    override fun start(toFragment: IBaseFragment) {
        delegate.start(toFragment)
    }

    override fun start(toFragment: IBaseFragment, @ISupportFragment.LaunchMode launchMode: Int) {
        delegate.start(toFragment, launchMode)
    }

    override fun startWithPopTo(
        toFragment: IBaseFragment,
        targetFragmentClass: Class<*>,
        includeTargetFragment: Boolean
    ) {
        delegate.startWithPopTo(toFragment, targetFragmentClass, includeTargetFragment)
    }

    override fun pop() {
        delegate.pop()
    }

    override fun popTo(targetFragmentClass: Class<*>, includeTargetFragment: Boolean) {
        delegate.popTo(targetFragmentClass, includeTargetFragment)
    }

    override fun popTo(
        targetFragmentClass: Class<*>,
        includeTargetFragment: Boolean,
        afterPopTransactionRunnable: Runnable
    ) {
        delegate.popTo(targetFragmentClass, includeTargetFragment, afterPopTransactionRunnable)
    }

    override fun popTo(
        targetFragmentClass: Class<*>,
        includeTargetFragment: Boolean,
        afterPopTransactionRunnable: Runnable,
        popAnim: Int
    ) {
        delegate.popTo(
            targetFragmentClass,
            includeTargetFragment,
            afterPopTransactionRunnable,
            popAnim
        )
    }

    override fun <F : IBaseFragment> findFragment(fragmentClass: Class<F>): F? =
        SupportHelper.findFragment(supportFragmentManager, fragmentClass)

    override fun getTopFragment(): ISupportFragment {
        return SupportHelper.getTopFragment(supportFragmentManager)
    }

    /**
     * Returns top fragment as desired class.
     *
     * If fragment is not a given class this returns null.
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ISupportFragment> getTopFragment(fragmentClass: Class<T>): T? {
        val topFragment = SupportHelper.getTopFragment(supportFragmentManager)
        return if (topFragment != null && topFragment.javaClass == fragmentClass) {
            topFragment as T
        } else null
    }
}
