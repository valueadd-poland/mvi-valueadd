package pl.valueadd.mvi.fragment.delegate.fragment

import android.os.Bundle
import pl.valueadd.mvi.fragment.mvi.IBaseViewState

interface MviFragmentDelegate<VS : IBaseViewState> {

    val restoredViewState: VS?

    fun onCreate(savedInstanceState: Bundle?)

    fun onStart()

    fun onStop()

    fun onDestroy()

    fun onSaveInstanceState(outState: Bundle)
}