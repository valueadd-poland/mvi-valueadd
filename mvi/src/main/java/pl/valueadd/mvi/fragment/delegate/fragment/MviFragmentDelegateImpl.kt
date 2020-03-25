package pl.valueadd.mvi.fragment.delegate.fragment

import android.os.Bundle
import pl.valueadd.mvi.presenter.BaseMviPresenter
import pl.valueadd.mvi.presenter.IBaseView

/**
 * Minimal implementation of contract between fragment and presenter.
 *
 * This API requires call every fragment's lifecycle callback event defined at [MviFragmentDelegate].
 */
open class MviFragmentDelegateImpl<V : IBaseView<*, *>>(
    protected val fragment: V,
    protected val presenter: BaseMviPresenter<*, *, *, V>
) : MviFragmentDelegate {

    override fun onCreate(savedInstanceState: Bundle?) {
        // no-op
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // no-op
    }

    override fun onStart() {
        presenter.attachView(fragment)
    }

    override fun onStop() {
        presenter.detachView()
    }

    override fun onDestroy() {
        presenter.destroy()
    }
}