package pl.valueadd.mvi.example.presentation.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import pl.valueadd.mvi.example.utility.dependencyinjection.DependencyUtil
import pl.valueadd.mvi.fragment.back.BackMviFragment
import pl.valueadd.mvi.fragment.mvi.BaseMviPresenter
import pl.valueadd.mvi.fragment.mvi.IBaseView
import pl.valueadd.mvi.fragment.mvi.IBaseViewState

abstract class AbstractBackMviFragment<V : IBaseView<VS, *>, VS : IBaseViewState, VI : IBaseView.IBaseIntent, P : BaseMviPresenter<VS, *, *, V>>(@LayoutRes layoutId: Int) :
    BackMviFragment<V, VS, VI, P>(layoutId) {

    override fun onCreate(savedInstanceState: Bundle?) {
        DependencyUtil.inject(requireActivity(), this)
        super.onCreate(savedInstanceState)
    }
}