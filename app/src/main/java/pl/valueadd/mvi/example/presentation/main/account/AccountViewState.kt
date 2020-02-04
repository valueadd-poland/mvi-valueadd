package pl.valueadd.mvi.example.presentation.main.account

import kotlinx.android.parcel.Parcelize
import org.apache.commons.lang3.StringUtils
import pl.valueadd.mvi.fragment.mvi.IBasePartialState
import pl.valueadd.mvi.fragment.mvi.IBaseViewState

@Parcelize
data class AccountViewState(
    var firstName: String = StringUtils.EMPTY,
    var surname: String = StringUtils.EMPTY,
    var email: String = StringUtils.EMPTY
) : IBaseViewState {
    sealed class PartialState :
        IBasePartialState {

        data class FirstNameChanged(val text: String) : PartialState()
        data class LastNameChanged(val text: String) : PartialState()
        data class EmailChanged(val text: String) : PartialState()
    }
}