package pl.valueadd.mvi.example.utility.dependencyinjection

import android.app.Activity
import android.app.Application
import androidx.fragment.app.FragmentActivity
import pl.valueadd.mvi.example.utility.dependencyinjection.annotation.ActivitySingleton
import pl.valueadd.mvi.example.utility.dependencyinjection.annotation.ApplicationSingleton
import toothpick.Scope
import toothpick.Toothpick
import toothpick.config.Module
import toothpick.smoothie.module.SmoothieActivityModule
import toothpick.smoothie.module.SmoothieApplicationModule

object DependencyUtil {

    private val scopes = HashMap<Activity, Scope>()
    private lateinit var appScope: Scope

    fun <T> inject(obj: T): T {
        Toothpick.inject(obj, appScope)
        return obj
    }

    fun <T> inject(activity: FragmentActivity, obj: T): T {
        Toothpick.inject(obj, getScopeIfExistsOrCreateNewOne(activity))
        return obj
    }

    fun openScopeAndInject(activity: FragmentActivity, vararg modules: Module) {
        val scope = Toothpick.openScopes(activity.application, activity)
        scope.installModules(SmoothieActivityModule(activity), *modules)
        scope.bindScopeAnnotation(ActivitySingleton::class.java)
        scopes[activity] = scope
        Toothpick.inject(activity, scope)
    }

    fun createAppScopeAndInject(application: Application, vararg modules: Module) {
        appScope = Toothpick.openScope(application)
        appScope.installModules(SmoothieApplicationModule(application), *modules)
        appScope.bindScopeAnnotation(ApplicationSingleton::class.java)
        Toothpick.inject(application, appScope)
    }

    fun closeScope(activity: FragmentActivity) {
        scopes.remove(activity)
        Toothpick.closeScope(activity)
    }

    private fun getScopeIfExistsOrCreateNewOne(activity: FragmentActivity): Scope {
        val scope = getScope(activity)
        return if (scope === null) {
            openScopeAndInject(activity)
            getScope(activity)!!
        } else scope
    }

    private fun getScope(activity: FragmentActivity): Scope? = scopes[activity]
}