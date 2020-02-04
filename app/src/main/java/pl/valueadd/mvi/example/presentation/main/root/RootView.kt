package pl.valueadd.mvi.example.presentation.main.root

import pl.valueadd.mvi.fragment.mvi.IBaseView

interface RootView :
    IBaseView<RootViewState, IBaseView.IBaseIntent> {

    fun navigateToAccountView()
}
