package pl.valueadd.mvi.fragment.mvi

interface IBaseMviFragment<P : BaseMviPresenter<*, *, *, *>> {
    var presenter: P
}