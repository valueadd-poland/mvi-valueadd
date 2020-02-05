package pl.valueadd.mvi.fragment.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import pl.valueadd.mvi.fragment.mvi.BaseMviPresenter
import pl.valueadd.mvi.fragment.mvi.IBaseView
import pl.valueadd.mvi.fragment.mvi.IBaseView.IBaseIntent
import pl.valueadd.mvi.fragment.mvi.IBaseViewState
import io.reactivex.disposables.CompositeDisposable
import java.util.UUID

abstract class BaseMviFragment<V : IBaseView<VS, *>, VS : IBaseViewState, VI : IBaseIntent, P : BaseMviPresenter<VS, *, *, V>>(@LayoutRes layoutId: Int) :
    BaseFragment(layoutId),
    IBaseView<VS, VI> {

    /* BaseView */

    /**
     * Returns presenter's disposable subscriptions of streams.
     */
    override val disposables: CompositeDisposable
        get() = presenter.disposables

    /* BaseMviFragment */

    abstract var presenter: P

    private val viewStateKey = "ARG_STATE_VIEW_${UUID.randomUUID()}"

    /* Life cycle */

    @Suppress("UNCHECKED_CAST")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this as V)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(viewStateKey, presenter.currentState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }
}