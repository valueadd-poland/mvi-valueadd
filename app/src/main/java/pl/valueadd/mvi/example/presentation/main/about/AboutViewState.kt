package pl.valueadd.mvi.example.presentation.main.about

import pl.valueadd.mvi.fragment.mvi.IBasePartialState
import pl.valueadd.mvi.fragment.mvi.IBaseViewState

class AboutViewState :
    IBaseViewState {

    sealed class PartialState :
        IBasePartialState
}