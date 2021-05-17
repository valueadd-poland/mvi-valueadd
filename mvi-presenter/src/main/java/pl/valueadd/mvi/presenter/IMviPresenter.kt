package pl.valueadd.mvi.presenter

interface IMviPresenter<V : IBaseView<*, *, *>> {

    fun initializeState(view: V)

    fun attachView(view: V)

    fun detachView()

    fun destroy()
}