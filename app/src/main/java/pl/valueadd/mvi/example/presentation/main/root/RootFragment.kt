package pl.valueadd.mvi.example.presentation.main.root

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_root.*
import pl.valueadd.mvi.example.R
import pl.valueadd.mvi.example.presentation.base.AbstractBaseMviFragment
import pl.valueadd.mvi.example.presentation.main.account.AccountFragment
import pl.valueadd.mvi.example.presentation.main.first.FirstFragment
import pl.valueadd.mvi.example.presentation.main.second.SecondFragment
import pl.valueadd.mvi.example.presentation.main.third.ThirdFragment
import pl.valueadd.mvi.example.utility.extension.show
import pl.valueadd.mvi.fragment.base.IBaseFragment
import pl.valueadd.mvi.fragment.mvi.IBaseView
import javax.inject.Inject

class RootFragment :
    AbstractBaseMviFragment<RootView, RootViewState, IBaseView.IBaseIntent, RootPresenter>(R.layout.fragment_root),
    RootView {

    companion object {

        fun createInstance(): RootFragment =
            RootFragment()

        private const val ROOT_NAVIGATION_SIZE = 3
    }

    @Inject
    override lateinit var presenter: RootPresenter

    @IdRes
    private val childFragmentContainer: Int = R.id.fragmentContainer

    private lateinit var toolbar: Toolbar

    private val rootFragments: Array<IBaseFragment?> = arrayOfNulls(ROOT_NAVIGATION_SIZE)

    @RootNavigation
    private var currentRootNavigation: Int = RootNavigation.FIRST

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {
            navigateToSelectedFragment(it)
            return@OnNavigationItemSelectedListener true
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeToolbar(view)
        initializeBottomNavigation()

        loadRootFragments()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_account -> {
                navigateToAccountView()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun navigateToAccountView() {

        val fragment = AccountFragment.createInstance()

        start(fragment)
    }

    override fun render(state: RootViewState) {
        //TODO("not implemented")
    }

    override fun provideViewIntents(): List<Observable<IBaseView.IBaseIntent>> = listOf()

    private fun navigateToSelectedFragment(item: MenuItem) {
        when (item.itemId) {
            R.id.navigation_first -> {
                navigateToFirstFragment()
                setToolbarTitle(R.string.first_title)
            }
            R.id.navigation_second -> {
                navigateToSecondFragment()
                setToolbarTitle(R.string.second_title)
            }
            R.id.navigation_third -> {
                navigateToThirdFragment()
                setToolbarTitle(R.string.third_title)
            }
        }
    }

    private fun navigateToFirstFragment() =
        navigateToView(RootNavigation.FIRST)

    private fun navigateToSecondFragment() =
        navigateToView(RootNavigation.SECOND)

    private fun navigateToThirdFragment() =
        navigateToView(RootNavigation.THIRD)

    private fun loadRootFragments() {

        val first = findChildFragment(getRootFragmentClass(RootNavigation.FIRST))

        if (first == null) {
            rootFragments[RootNavigation.FIRST] = FirstFragment.createInstance()
            rootFragments[RootNavigation.SECOND] = SecondFragment.createInstance()
            rootFragments[RootNavigation.THIRD] = ThirdFragment.createInstance()

            supportDelegate.loadMultipleRootFragment(
                childFragmentContainer, RootNavigation.FIRST,
                rootFragments[RootNavigation.FIRST],
                rootFragments[RootNavigation.SECOND],
                rootFragments[RootNavigation.THIRD]
            )
        } else {
            rootFragments[RootNavigation.FIRST] = first
            rootFragments[RootNavigation.SECOND] = findChildFragment(SecondFragment::class.java)
            rootFragments[RootNavigation.THIRD] = findChildFragment(ThirdFragment::class.java)
        }
    }

    private fun getRootFragmentClass(@RootNavigation type: Int): Class<out IBaseFragment> =
        when (type) {
            RootNavigation.FIRST -> FirstFragment::class.java
            RootNavigation.SECOND -> SecondFragment::class.java
            RootNavigation.THIRD -> ThirdFragment::class.java
            else -> throw IllegalArgumentException("Invalid type ($type) has been passed.")
        }

    private fun setToolbarTitle(@StringRes resId: Int): Unit =
        toolbar.setTitle(resId)

    private fun navigateToView(@RootNavigation type: Int) {

        val currentFragment: IBaseFragment = requireNotNull(rootFragments[currentRootNavigation])

        val selectedFragment: IBaseFragment = requireNotNull(rootFragments[type])

        showHideFragment(
            showFragment = selectedFragment,
            hideFragment = currentFragment
        )

        currentRootNavigation = type
    }

    private fun initializeBottomNavigation() {
        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private fun initializeToolbar(view: View) {
        toolbar = view
            .findViewById<AppBarLayout>(R.id.baseAppBarLayout)
            .findViewById(R.id.baseToolbar)

        toolbar.apply {

            inflateMenu(R.menu.main_menu)

            setOnMenuItemClickListener(::onOptionsItemSelected)

            setToolbarTitle(R.string.first_title)

            menu.show(R.id.action_account)
        }
    }
}