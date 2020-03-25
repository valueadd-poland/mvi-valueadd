package pl.valueadd.mvi.presenter

interface IMviPresenter<V : IBaseView<*, *>> {

    fun attachView(view: V)

    fun detachView()

    fun destroy()
}