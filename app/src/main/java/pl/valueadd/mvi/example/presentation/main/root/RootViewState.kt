package pl.valueadd.mvi.example.presentation.main.root

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import pl.valueadd.mvi.presenter.IBasePartialState
import pl.valueadd.mvi.presenter.IBaseViewState

@Parcelize
class RootViewState :
    IBaseViewState, Parcelable {

    sealed class PartialState :
        IBasePartialState
}