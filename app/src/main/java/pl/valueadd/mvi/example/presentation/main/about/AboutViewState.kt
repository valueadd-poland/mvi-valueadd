package pl.valueadd.mvi.example.presentation.main.about

import kotlinx.android.parcel.Parcelize
import pl.valueadd.mvi.IBaseViewState
import pl.valueadd.mvi.presenter.IBasePartialState

@Parcelize
class AboutViewState :
    IBaseViewState {

    sealed class PartialState :
        IBasePartialState
}