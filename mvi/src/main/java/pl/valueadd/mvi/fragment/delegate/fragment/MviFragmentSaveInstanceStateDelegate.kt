package pl.valueadd.mvi.fragment.delegate.fragment

import pl.valueadd.mvi.presenter.IBaseViewState

interface MviFragmentSaveInstanceStateDelegate<VS : IBaseViewState> {

    val restoredViewState: VS?
}