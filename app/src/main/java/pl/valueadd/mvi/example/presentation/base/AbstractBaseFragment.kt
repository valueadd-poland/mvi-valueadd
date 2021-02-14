package pl.valueadd.mvi.example.presentation.base

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import pl.valueadd.mvi.example.utility.dependencyinjection.DependencyUtil
import pl.valueadd.mvi.fragment.base.BaseFragment

abstract class AbstractBaseFragment<Binding : ViewBinding> : BaseFragment<Binding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        DependencyUtil.inject(requireActivity(), this)
        super.onCreate(savedInstanceState)
    }
}