package pl.valueadd.mvi.fragment.delegate.fragment

import android.os.Bundle
import pl.valueadd.mvi.fragment.mvi.BaseMviPresenter
import pl.valueadd.mvi.fragment.mvi.IBaseView
import pl.valueadd.mvi.fragment.mvi.IBaseViewState

class MviFragmentDelegateImpl<V : IBaseView<VS, *>, VS : IBaseViewState>(
    private val shouldHandleSaveInstanceState: Boolean,
    private val fragment: V,
    private val presenter: BaseMviPresenter<VS, *, *, V>
) : MviFragmentDelegate<VS> {

    companion object {
        private const val VIEW_STATE_BUNDLE_KEY = "VIEW_STATE_BUNDLE_KEY"
    }

    override var restoredViewState: VS? = null
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        if (shouldHandleSaveInstanceState) {
            this.restoredViewState = savedInstanceState?.getParcelable(VIEW_STATE_BUNDLE_KEY)
        }
    }

    override fun onStart() {
        presenter.attachView(fragment)
    }

    override fun onStop() {
        presenter.detachView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (shouldHandleSaveInstanceState) {
            outState.putParcelable(VIEW_STATE_BUNDLE_KEY, presenter.currentState)
        }
    }

    override fun onDestroy() {
        presenter.destroy()
    }
}