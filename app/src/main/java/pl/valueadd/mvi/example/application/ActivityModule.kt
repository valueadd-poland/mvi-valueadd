package pl.valueadd.mvi.example.application

import android.app.Activity
import android.content.Context
import toothpick.config.Module

class ActivityModule(activity: Activity) : Module() {

    init {
        bind(Context::class.java).toInstance(activity)
        bind(Activity::class.java).toInstance(activity)
    }
}