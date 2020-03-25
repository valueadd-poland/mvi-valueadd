package pl.valueadd.mvi.fragment.delegate.fragment

import android.os.Bundle
import android.os.Parcelable
import pl.valueadd.mvi.presenter.BaseMviPresenter
import pl.valueadd.mvi.presenter.IBaseView
import pl.valueadd.mvi.presenter.IBaseViewState

/**
 * Extended [implementation][MviFragmentDelegateImpl] of fragment's MVI which handles
 * that state can be restored after killing process by system.
 *
 * @see MviFragmentDelegateImpl
 */
class MviFragmentSaveInstanceStateDelegateImpl<V : IBaseView<VS, *>, VS>(
    fragment: V,
    presenter: BaseMviPresenter<VS, *, *, V>
) : MviFragmentDelegateImpl<V>(fragment, presenter),
    MviFragmentSaveInstanceStateDelegate<VS> where VS : IBaseViewState, VS : Parcelable {

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
            presenter.currentState as Parcelable
        )
    }
}