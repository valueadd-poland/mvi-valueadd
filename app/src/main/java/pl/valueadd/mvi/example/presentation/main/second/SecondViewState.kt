package pl.valueadd.mvi.example.presentation.main.second

import kotlinx.android.parcel.Parcelize
import org.apache.commons.lang3.StringUtils.EMPTY
import pl.valueadd.mvi.IBaseViewState
import pl.valueadd.mvi.example.presentation.main.second.item.ExampleItem
import pl.valueadd.mvi.presenter.IBasePartialState

@Parcelize
data class SecondViewState(
    val list: List<ExampleItem> = emptyList(),
    val error: String = EMPTY
) : IBaseViewState {

    sealed class PartialState :
        IBasePartialState {
        data class LoadExamplesSuccess(val list: List<ExampleItem>) : PartialState()
        data class LoadExamplesFail(val error: Throwable) : PartialState()
    }
}