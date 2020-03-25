package pl.valueadd.mvi.example.presentation.main.third

import kotlinx.android.parcel.Parcelize
import pl.valueadd.mvi.IBaseViewState
import pl.valueadd.mvi.presenter.IBasePartialState

@Parcelize
class ThirdViewState :
    IBaseViewState {

    sealed class PartialState :
        IBasePartialState
}