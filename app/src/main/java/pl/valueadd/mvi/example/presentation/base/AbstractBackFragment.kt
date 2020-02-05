package pl.valueadd.mvi.example.presentation.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import pl.valueadd.mvi.example.utility.dependencyinjection.DependencyUtil
import pl.valueadd.mvi.fragment.back.BackFragment

abstract class AbstractBackFragment(@LayoutRes layoutId: Int) : BackFragment(layoutId) {

    override fun onCreate(savedInstanceState: Bundle?) {
        DependencyUtil.inject(requireActivity(), this)
        super.onCreate(savedInstanceState)
    }
}