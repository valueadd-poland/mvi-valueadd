package pl.valueadd.mvi.example.application

import androidx.multidex.MultiDexApplication
import pl.valueadd.mvi.example.presentation.PresentationModule
import pl.valueadd.mvi.example.utility.dependencyinjection.DependencyUtil
import me.yokeyword.fragmentation.Fragmentation
import pl.valueadd.mvi.example.BuildConfig
import toothpick.config.Module

class MviApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        initToothpick()

        Fragmentation.builder()
            .stackViewMode(Fragmentation.BUBBLE)
            .debug(BuildConfig.DEBUG)
            .handleException {
                // TODO Crashlytics log exception...
            }
            .install()
    }

    private fun initToothpick() =
        DependencyUtil.createAppScopeAndInject(this, *provideModules())

    private fun provideModules(): Array<Module> =
        arrayOf(
            ApplicationModule(this),
            PresentationModule()
        )
}