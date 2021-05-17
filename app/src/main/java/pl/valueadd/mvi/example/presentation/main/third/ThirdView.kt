package pl.valueadd.mvi.example.presentation.main.third

import pl.valueadd.mvi.presenter.IBaseView

interface ThirdView :
    IBaseView<ThirdViewState, IBaseView.IBaseIntent, IBaseView.IBaseEffect>