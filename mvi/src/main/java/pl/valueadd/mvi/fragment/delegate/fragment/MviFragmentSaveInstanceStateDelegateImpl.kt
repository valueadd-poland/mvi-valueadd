package pl.valueadd.mvi.fragment.delegate.fragment

import android.os.Bundle
import android.os.Parcelable
import pl.valueadd.mvi.IBaseViewState
import pl.valueadd.mvi.presenter.BaseMviPresenter
import pl.valueadd.mvi.presenter.IBaseView

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
            savedInstanceState?.getParcelable<VS>(VIEW_STATE_BUNDLE_KEY)
        super.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(
            VIEW_STATE_BUNDLE_KEY,
            presenter.currentState as Parcelable
        )
    }
}