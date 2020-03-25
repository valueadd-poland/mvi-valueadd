package pl.valueadd.mvi.example.presentation.base

import android.os.Parcelable
import io.reactivex.android.schedulers.AndroidSchedulers
import pl.valueadd.mvi.presenter.BaseMviPresenter
import pl.valueadd.mvi.presenter.IBasePartialState
import pl.valueadd.mvi.presenter.IBaseView
import pl.valueadd.mvi.presenter.IBaseViewState

abstract class AbstractPresenter<VS, PS : IBasePartialState, VI : IBaseView.IBaseIntent, V : IBaseView<VS, VI>> :
    BaseMviPresenter<VS, PS, VI, V>(AndroidSchedulers.mainThread())
        where VS : IBaseViewState, VS : Parcelable