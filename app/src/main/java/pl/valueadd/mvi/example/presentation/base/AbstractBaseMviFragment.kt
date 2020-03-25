package pl.valueadd.mvi.example.presentation.base

import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.LayoutRes
import pl.valueadd.mvi.example.utility.dependencyinjection.DependencyUtil
import pl.valueadd.mvi.fragment.base.BaseMviFragment
import pl.valueadd.mvi.fragment.delegate.fragment.MviFragmentSaveInstanceStateDelegateImpl
import pl.valueadd.mvi.presenter.BaseMviPresenter
import pl.valueadd.mvi.presenter.IBaseView
import pl.valueadd.mvi.presenter.IBaseViewState

abstract class AbstractBaseMviFragment<V : IBaseView<VS, *>, VS, VI : IBaseView.IBaseIntent, P : BaseMviPresenter<VS, *, *, V>>(@LayoutRes layoutId: Int) :
    BaseMviFragment<V, VS, VI, P>(layoutId) where VS : IBaseViewState, VS : Parcelable {

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