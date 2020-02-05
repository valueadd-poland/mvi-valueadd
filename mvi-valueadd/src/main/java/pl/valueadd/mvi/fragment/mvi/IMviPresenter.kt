package pl.valueadd.mvi.fragment.mvi

interface IMviPresenter<V : IBaseView<*, *>> {

    fun attachView(view: V)

    fun detachView()

    fun destroy()

}