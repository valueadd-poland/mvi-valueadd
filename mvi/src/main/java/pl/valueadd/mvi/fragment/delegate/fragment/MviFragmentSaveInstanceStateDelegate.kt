package pl.valueadd.mvi.fragment.delegate.fragment

import pl.valueadd.mvi.fragment.mvi.IBaseViewState

interface MviFragmentSaveInstanceStateDelegate<VS : IBaseViewState> {

    val restoredViewState: VS?
}