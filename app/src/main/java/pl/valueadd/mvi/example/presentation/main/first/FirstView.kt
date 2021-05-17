package pl.valueadd.mvi.example.presentation.main.first

import pl.valueadd.mvi.presenter.IBaseView

interface FirstView :
    IBaseView<FirstViewState, FirstView.Intent, FirstView.Effect> {

    sealed class Intent : IBaseView.IBaseIntent {
        object IncreaseCount : Intent()
        object DecreaseCount : Intent()
        object ProcessData : Intent()
    }

    sealed class Effect : IBaseView.IBaseEffect {
        data class ShowMessage(val message: String) : Effect()
    }
}
