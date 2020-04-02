package pl.valueadd.mvi

import android.os.Parcelable
import pl.valueadd.mvi.presenter.IBaseViewState

/**
 * Alias interface for [IBaseViewState] & [Parcelable].
 */
interface IBaseViewState : IBaseViewState, Parcelable