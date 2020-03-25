package pl.valueadd.mvi.example.presentation.main.account

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.apache.commons.lang3.StringUtils
import pl.valueadd.mvi.presenter.IBasePartialState
import pl.valueadd.mvi.presenter.IBaseViewState

@Parcelize
data class AccountViewState(
    var firstName: String = StringUtils.EMPTY,
    var surname: String = StringUtils.EMPTY,
    var email: String = StringUtils.EMPTY
) : IBaseViewState, Parcelable {
    sealed class PartialState :
        IBasePartialState {

        data class OnViewDestroyed(
            var firstName: String = StringUtils.EMPTY,
            var surname: String = StringUtils.EMPTY,
            var email: String = StringUtils.EMPTY
        ) : PartialState()
    }
}