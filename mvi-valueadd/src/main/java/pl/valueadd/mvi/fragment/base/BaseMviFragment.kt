package pl.valueadd.mvi.fragment.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import pl.valueadd.mvi.fragment.mvi.BaseMviPresenter
import pl.valueadd.mvi.fragment.mvi.IBaseView
import pl.valueadd.mvi.fragment.mvi.IBaseView.IBaseIntent
import pl.valueadd.mvi.fragment.mvi.IBaseViewState
import java.util.UUID

abstract class BaseMviFragment<V : IBaseView<VS, *>, VS : IBaseViewState, VI : IBaseIntent, P : BaseMviPresenter<VS, *, *, V>>(@LayoutRes layoutId: Int) :
    BaseFragment(layoutId),
    IBaseView<VS, VI> {

    /* BaseMviFragment */

    abstract var presenter: P

    /**
     * Returns disposable container of subscriptions.
     */
    protected var disposables: CompositeDisposable =
        CompositeDisposable()

    private val viewStateKey = "ARG_STATE_VIEW_${UUID.randomUUID()}"

    /* IBaseView */

    /**
     * @see [Disposable.isDisposed]
     */
    final override fun isDisposed(): Boolean =
        disposables.isDisposed

    /**
     * @see [Disposable.dispose]
     */
    final override fun dispose(): Unit =
        disposables.dispose()

    /* Life cycle */

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(viewStateKey, presenter.currentState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        disposables = CompositeDisposable()
    }

    @Suppress("UNCHECKED_CAST")
    override fun onStart() {
        super.onStart()
        presenter.attachView(this as V)
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dispose()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }
}