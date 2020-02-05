package pl.valueadd.mvi.fragment.mvi

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

interface IMviPresenter<V : IBaseView<*,*>> : Disposable {

    /**
     * Contains disposable subscription of streams.
     */
    val disposables: CompositeDisposable

    /**
     * Add subscription to composite.
     */
    fun addDisposable(disposable: Disposable): Boolean =
        disposables.add(disposable)

    fun attachView(view: V)

    fun detachView()

    fun destroy()

}