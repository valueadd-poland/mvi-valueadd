package pl.valueadd.mvi.example.application

import androidx.multidex.MultiDexApplication
import pl.valueadd.mvi.example.presentation.PresentationModule
import pl.valueadd.mvi.example.utility.dependencyinjection.DependencyUtil
import toothpick.config.Module

class MviApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        initToothpick()
    }

    private fun initToothpick() =
        DependencyUtil.createAppScopeAndInject(this, *provideModules())

    private fun provideModules(): Array<Module> =
        arrayOf(
            ApplicationModule(this),
            PresentationModule()
        )
}