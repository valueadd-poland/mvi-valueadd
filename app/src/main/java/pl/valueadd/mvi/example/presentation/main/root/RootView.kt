package pl.valueadd.mvi.example.presentation.main.root

import pl.valueadd.mvi.presenter.IBaseView

interface RootView :
    IBaseView<RootViewState, IBaseView.IBaseIntent>
