package pl.valueadd.mvi.example.presentation.main.account

import org.apache.commons.lang3.StringUtils
import pl.valueadd.mvi.fragment.mvi.IBasePartialState
import pl.valueadd.mvi.fragment.mvi.IBaseViewState

data class AccountViewState(
    var firstName: String = StringUtils.EMPTY,
    var surname: String = StringUtils.EMPTY,
    var email: String = StringUtils.EMPTY
) : IBaseViewState {
    sealed class PartialState :
        IBasePartialState {

        data class OnViewDestroyed(
            var firstName: String = StringUtils.EMPTY,
            var surname: String = StringUtils.EMPTY,
            var email: String = StringUtils.EMPTY
        ) : PartialState()
    }
}