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

abstract class BaseMviFragment<V : IBaseView<VS, *>, VS : IBaseViewState, VI : IBaseIntent, P : BaseMviPresenter<VS, *, *, V>>(@LayoutRes layoutId: Int) :
    BaseFragment(layoutId),
    IBaseView<VS, VI> {

    companion object {
        private const val VIEW_STATE_BUNDLE_KEY = "VIEW_STATE_BUNDLE_KEY"
    }

    /* BaseMviFragment */

    abstract var presenter: P

    /**
     * Returns disposable container of subscriptions.
     */
    protected var disposables: CompositeDisposable =
        CompositeDisposable()

    /*
     * View state which can be restored after killing process by system.
     */
    protected var restoredViewState: VS? = null
        private set

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.restoredViewState = savedInstanceState?.getParcelable(VIEW_STATE_BUNDLE_KEY)
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(VIEW_STATE_BUNDLE_KEY, presenter.currentState)
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