package pl.valueadd.mvi.fragment.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import pl.valueadd.mvi.fragment.mvi.BaseMviPresenter
import pl.valueadd.mvi.fragment.mvi.IBaseMviFragment
import pl.valueadd.mvi.fragment.mvi.IBaseView
import pl.valueadd.mvi.fragment.mvi.IBaseView.IBaseIntent
import pl.valueadd.mvi.fragment.mvi.IBaseViewState
import pl.valueadd.mvi.fragment.mvi.delegation.MviFragmentDelegate
import pl.valueadd.mvi.fragment.mvi.delegation.MviFragmentDelegateImpl

abstract class BaseMviFragment<V : IBaseView<VS, *>, VS : IBaseViewState, VI : IBaseIntent, P : BaseMviPresenter<VS, *, *, V>>(
    @LayoutRes layoutId: Int
) : BaseFragment(layoutId), IBaseView<VS, VI>, IBaseMviFragment<P> {

    private val delegate: MviFragmentDelegate
        by MviFragmentDelegateImpl<V, P>()

    /* BaseView */

    /**
     * Returns disposable container of subscriptions.
     */
    final override var disposables: CompositeDisposable =
        CompositeDisposable()

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

    /* BaseMviFragment */

    abstract var presenter: P

    /* Life cycle */

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        disposables = CompositeDisposable()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dispose()
    }
}