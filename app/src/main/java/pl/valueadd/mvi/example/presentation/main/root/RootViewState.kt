package pl.valueadd.mvi.example.presentation.main.root

import pl.valueadd.mvi.fragment.mvi.IBasePartialState
import pl.valueadd.mvi.fragment.mvi.IBaseViewState

class RootViewState :
    IBaseViewState {

    sealed class PartialState :
        IBasePartialState
}