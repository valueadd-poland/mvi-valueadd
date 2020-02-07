package pl.valueadd.mvi.fragment.delegate.fragment

import pl.valueadd.mvi.fragment.mvi.BaseMviPresenter
import pl.valueadd.mvi.fragment.mvi.IBaseView

class MviFragmentDelegateImpl<V : IBaseView<*, *>>(
    private val fragment: V,
    private val presenter: BaseMviPresenter<*, *, *, V>
) : MviFragmentDelegate {

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