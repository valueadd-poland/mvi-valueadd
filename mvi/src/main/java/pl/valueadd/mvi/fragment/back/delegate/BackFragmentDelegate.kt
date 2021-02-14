package pl.valueadd.mvi.fragment.back.delegate

import android.os.Bundle
import android.view.View

interface BackFragmentDelegate : BackFragmentDelegateCallback {

    fun onViewCreated(view: View, savedInstanceState: Bundle?)
}