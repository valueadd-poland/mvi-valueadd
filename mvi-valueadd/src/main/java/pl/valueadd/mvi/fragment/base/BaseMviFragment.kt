package pl.valueadd.mvi.fragment.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import pl.valueadd.mvi.fragment.delegate.fragment.MviFragmentDelegate
import pl.valueadd.mvi.fragment.delegate.fragment.MviFragmentDelegateImpl
import pl.valueadd.mvi.fragment.mvi.BaseMviPresenter
import pl.valueadd.mvi.fragment.mvi.IBaseView
import pl.valueadd.mvi.fragment.mvi.IBaseView.IBaseIntent
import pl.valueadd.mvi.fragment.mvi.IBaseViewState

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

    @Suppress("UNCHECKED_CAST")
    protected open val mviDelegate: MviFragmentDelegate
            by lazy { MviFragmentDelegateImpl(this as V, presenter) }

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
        mviDelegate.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        disposables = CompositeDisposable()
    }

    override fun onStart() {
        super.onStart()
        mviDelegate.onStart()
    }

    override fun onStop() {
        super.onStop()
        mviDelegate.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mviDelegate.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dispose()
    }

    override fun onDestroy() {
        super.onDestroy()
        mviDelegate.onDestroy()
    }
}