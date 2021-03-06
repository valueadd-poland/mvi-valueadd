package pl.valueadd.mvi.example.presentation.base

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import pl.valueadd.mvi.presenter.BaseMviPresenter
import pl.valueadd.mvi.presenter.IBasePartialState
import pl.valueadd.mvi.presenter.IBaseView
import pl.valueadd.mvi.presenter.IBaseViewState

abstract class AbstractPresenter<VS : IBaseViewState, PS : IBasePartialState, VI : IBaseView.IBaseIntent, VE : IBaseView.IBaseEffect, V : IBaseView<VS, VI, VE>> :
    BaseMviPresenter<VS, PS, VI, VE, V>(AndroidSchedulers.mainThread()) {

    override fun onError(throwable: Throwable) {
        super.onError(throwable)
        Log.e(this::class.java.simpleName, "MVI has an error: ${throwable.message}", throwable)
    }
}