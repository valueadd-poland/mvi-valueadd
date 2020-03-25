package pl.valueadd.mvi.example.presentation.main.first

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.apache.commons.lang3.StringUtils.EMPTY
import org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO
import pl.valueadd.mvi.presenter.IBasePartialState
import pl.valueadd.mvi.presenter.IBaseViewState

@Parcelize
data class FirstViewState(
    val count: Int = INTEGER_ZERO,
    val error: String = EMPTY,
    val value: String = EMPTY
) : IBaseViewState, Parcelable {

    sealed class PartialState :
        IBasePartialState {
        data class ProcessDataSuccess(val value: String) : PartialState()
        data class ProcessDataFail(val error: Throwable) : PartialState()
        data class ChangeCountSuccess(val count: Int) : PartialState()
        data class ChangeCountFail(val error: Throwable) : PartialState()
    }
}