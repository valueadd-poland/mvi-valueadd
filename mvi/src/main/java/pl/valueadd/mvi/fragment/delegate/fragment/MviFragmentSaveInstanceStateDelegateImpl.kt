package pl.valueadd.mvi.fragment.delegate.fragment

import android.os.Bundle
import pl.valueadd.mvi.fragment.mvi.BaseMviPresenter
import pl.valueadd.mvi.fragment.mvi.IBaseView
import pl.valueadd.mvi.fragment.mvi.IBaseViewState

/**
 * Extended [implementation][MviFragmentDelegateImpl] of fragment's MVI which handles
 * that state can be restored after killing process by system.
 *
 * @see MviFragmentDelegateImpl
 */
class MviFragmentSaveInstanceStateDelegateImpl<V : IBaseView<VS, *>, VS : IBaseViewState>(
    fragment: V,
    presenter: BaseMviPresenter<VS, *, *, V>
) : MviFragmentDelegateImpl<V>(fragment, presenter),
    MviFragmentSaveInstanceStateDelegate<VS> {

    companion object {
        private const val VIEW_STATE_BUNDLE_KEY = "VIEW_STATE_BUNDLE_KEY"
    }

    override var restoredViewState: VS? = null
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        this.restoredViewState =
            savedInstanceState?.getParcelable(VIEW_STATE_BUNDLE_KEY)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(
            VIEW_STATE_BUNDLE_KEY,
            presenter.currentState
        )
    }
}