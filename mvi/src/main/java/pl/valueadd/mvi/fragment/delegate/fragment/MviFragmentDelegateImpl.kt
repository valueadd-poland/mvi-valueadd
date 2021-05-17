package pl.valueadd.mvi.fragment.delegate.fragment

import android.os.Bundle
import androidx.annotation.CallSuper
import pl.valueadd.mvi.presenter.BaseMviPresenter
import pl.valueadd.mvi.presenter.IBaseView

/**
 * Minimal implementation of contract between fragment and presenter.
 *
 * This API requires call every fragment's lifecycle callback event defined at [MviFragmentDelegate].
 */
open class MviFragmentDelegateImpl<V : IBaseView<*, *, *>>(
    protected val fragment: V,
    protected val presenter: BaseMviPresenter<*, *, *, *, V>
) : MviFragmentDelegate {

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        presenter.initializeState(fragment)
    }

    @CallSuper
    override fun onSaveInstanceState(outState: Bundle) {
        // no-op
    }

    @CallSuper
    override fun onStart() {
        presenter.attachView(fragment)
    }

    @CallSuper
    override fun onStop() {
        presenter.detachView()
    }

    @CallSuper
    override fun onDestroy() {
        presenter.destroy()
    }
}