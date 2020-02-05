package pl.valueadd.mvi.fragment.mvi.delegation

import android.os.Bundle
import androidx.lifecycle.LifecycleObserver
import pl.valueadd.mvi.fragment.mvi.BaseMviPresenter

interface MviFragmentDelegate : LifecycleObserver {

    val viewStateKey: String

    fun onSaveInstanceState(outState: Bundle)

    fun onStart()

    fun onStop()

    fun onDestroy()
}