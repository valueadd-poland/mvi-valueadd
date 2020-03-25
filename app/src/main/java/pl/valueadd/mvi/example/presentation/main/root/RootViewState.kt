package pl.valueadd.mvi.example.presentation.main.root

import kotlinx.android.parcel.Parcelize
import pl.valueadd.mvi.IBaseViewState
import pl.valueadd.mvi.presenter.IBasePartialState

@Parcelize
class RootViewState :
    IBaseViewState {

    sealed class PartialState :
        IBasePartialState
}