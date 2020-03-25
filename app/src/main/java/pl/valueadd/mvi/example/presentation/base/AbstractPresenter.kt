package pl.valueadd.mvi.example.presentation.base

import io.reactivex.android.schedulers.AndroidSchedulers
import pl.valueadd.mvi.IBaseViewState
import pl.valueadd.mvi.presenter.BaseMviPresenter
import pl.valueadd.mvi.presenter.IBasePartialState
import pl.valueadd.mvi.presenter.IBaseView

abstract class AbstractPresenter<VS : IBaseViewState, PS : IBasePartialState, VI : IBaseView.IBaseIntent, V : IBaseView<VS, VI>> :
    BaseMviPresenter<VS, PS, VI, V>(AndroidSchedulers.mainThread())