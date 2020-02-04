package pl.valueadd.mvi.example.presentation.main

import android.os.Bundle
import pl.valueadd.mvi.example.presentation.base.AbstractActivity
import pl.valueadd.mvi.example.presentation.main.root.RootFragment

class MainActivity : AbstractActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadRootFragment()
    }

    private fun loadRootFragment() {

        val fragment = findFragment(RootFragment::class.java)

        if (fragment == null) {
            loadRootFragment(fragmentContainer, RootFragment.createInstance())
        }
    }
}