package pl.valueadd.mvi.example.presentation.main.about

import pl.valueadd.mvi.presenter.IBaseView

interface AboutView :
    IBaseView<AboutViewState, IBaseView.IBaseIntent>
