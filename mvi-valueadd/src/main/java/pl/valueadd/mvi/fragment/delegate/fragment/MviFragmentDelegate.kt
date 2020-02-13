package pl.valueadd.mvi.fragment.delegate.fragment

import android.os.Bundle

interface MviFragmentDelegate {

    fun onCreate(savedInstanceState: Bundle?)

    fun onSaveInstanceState(outState: Bundle)

    fun onStart()

    fun onStop()

    fun onDestroy()
}