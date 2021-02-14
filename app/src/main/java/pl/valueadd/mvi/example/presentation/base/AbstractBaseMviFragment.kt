package pl.valueadd.mvi.example.presentation.base

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import pl.valueadd.mvi.IBaseViewState
import pl.valueadd.mvi.example.utility.dependencyinjection.DependencyUtil
import pl.valueadd.mvi.fragment.base.BaseMviFragment
import pl.valueadd.mvi.fragment.delegate.fragment.MviFragmentSaveInstanceStateDelegateImpl
import pl.valueadd.mvi.presenter.BaseMviPresenter
import pl.valueadd.mvi.presenter.IBaseView

abstract class AbstractBaseMviFragment<V : IBaseView<VS, *>, VS : IBaseViewState, VI : IBaseView.IBaseIntent, P : BaseMviPresenter<VS, *, *, V>, Binding : ViewBinding> :
    BaseMviFragment<V, VS, VI, P, Binding>() {

    protected val restoredViewState: VS?
        get() = mviDelegate.restoredViewState

    @Suppress("UNCHECKED_CAST")
    override val mviDelegate: MviFragmentSaveInstanceStateDelegateImpl<V, VS>
            by lazy {
                MviFragmentSaveInstanceStateDelegateImpl(
                    this as V,
                    presenter
                )
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        DependencyUtil.inject(requireActivity(), this)
        super.onCreate(savedInstanceState)
    }
}