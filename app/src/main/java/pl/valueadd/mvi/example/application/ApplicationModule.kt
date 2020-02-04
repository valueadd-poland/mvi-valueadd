package pl.valueadd.mvi.example.application

import android.app.Application
import android.content.Context
import toothpick.config.Module

class ApplicationModule(application: Application) : Module() {

    init {
        bind(Context::class.java).toInstance(application)
        bind(Application::class.java).toInstance(application)
    }
}