package pl.valueadd.mvi.example.presentation.main.second

import pl.valueadd.mvi.presenter.IBaseView

interface SecondView :
    IBaseView<SecondViewState, IBaseView.IBaseIntent, IBaseView.IBaseEffect>