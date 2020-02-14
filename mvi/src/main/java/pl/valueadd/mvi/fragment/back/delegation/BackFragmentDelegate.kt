package pl.valueadd.mvi.fragment.back.delegation

import android.os.Bundle
import android.view.View
import me.yokeyword.fragmentation.anim.FragmentAnimator

interface BackFragmentDelegate : BackFragmentDelegateCallback {

    fun onViewCreated(view: View, savedInstanceState: Bundle?)

    fun onCreateFragmentAnimator(): FragmentAnimator
}