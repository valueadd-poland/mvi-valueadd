package pl.valueadd.mvi.example.presentation.main.about

import kotlinx.android.parcel.Parcelize
import pl.valueadd.mvi.fragment.mvi.IBasePartialState
import pl.valueadd.mvi.fragment.mvi.IBaseViewState

@Parcelize
class AboutViewState :
    IBaseViewState {

    sealed class PartialState :
        IBasePartialState
}