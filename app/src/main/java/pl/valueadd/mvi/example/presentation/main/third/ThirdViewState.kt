package pl.valueadd.mvi.example.presentation.main.third

import kotlinx.android.parcel.Parcelize
import pl.valueadd.mvi.fragment.mvi.IBasePartialState
import pl.valueadd.mvi.fragment.mvi.IBaseViewState

@Parcelize
class ThirdViewState :
    IBaseViewState {

    sealed class PartialState :
        IBasePartialState
}