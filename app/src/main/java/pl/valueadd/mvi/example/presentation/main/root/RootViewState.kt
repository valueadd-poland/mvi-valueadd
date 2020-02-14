package pl.valueadd.mvi.example.presentation.main.root

import kotlinx.android.parcel.Parcelize
import pl.valueadd.mvi.fragment.mvi.IBasePartialState
import pl.valueadd.mvi.fragment.mvi.IBaseViewState

@Parcelize
class RootViewState :
    IBaseViewState {

    sealed class PartialState :
        IBasePartialState
}