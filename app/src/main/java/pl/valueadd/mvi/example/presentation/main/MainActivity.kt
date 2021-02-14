package pl.valueadd.mvi.example.presentation.main

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import pl.valueadd.mvi.activity.ActivityBindingInflater
import pl.valueadd.mvi.example.R
import pl.valueadd.mvi.example.databinding.MainActivityBinding
import pl.valueadd.mvi.example.presentation.base.AbstractActivity

class MainActivity : AbstractActivity<MainActivityBinding>() {

    override val bindingInflater: ActivityBindingInflater<MainActivityBinding>
        get() = MainActivityBinding::inflate

    private val navController
        get() = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadRootFragment()
    }

    private val topDestinationIds: Set<Int> = setOf(
        R.id.firstFragment,
        R.id.secondFragment,
        R.id.thirdFragment
    )

    private fun loadRootFragment(): Unit = with(requireBinding) {
        bottomNavigation.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id !in topDestinationIds) {
                bottomNavigation.visibility = View.GONE
            } else {
                bottomNavigation.visibility = View.VISIBLE
            }
        }
    }
}