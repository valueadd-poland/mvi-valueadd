package pl.valueadd.mvi.example.presentation.base

import android.os.Bundle
import pl.valueadd.mvi.activity.BaseActivity
import pl.valueadd.mvi.example.application.ActivityModule
import pl.valueadd.mvi.example.utility.dependencyinjection.DependencyUtil

abstract class AbstractActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        DependencyUtil.openScopeAndInject(this, ActivityModule(this))
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        DependencyUtil.closeScope(this)
    }
}