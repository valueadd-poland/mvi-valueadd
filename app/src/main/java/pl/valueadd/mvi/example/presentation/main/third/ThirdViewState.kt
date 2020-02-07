package pl.valueadd.mvi.example.presentation.main.third

import pl.valueadd.mvi.fragment.mvi.IBasePartialState
import pl.valueadd.mvi.fragment.mvi.IBaseViewState

class ThirdViewState :
    IBaseViewState {

    sealed class PartialState :
        IBasePartialState
}