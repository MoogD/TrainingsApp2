package com.example.trainingsapp.injections

import android.app.Application
import android.content.Context
import com.example.trainingsapp.utils.AndroidUtils
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class TrainingsApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    val component: TrainingApplicationComponent by lazy {
        DaggerTrainingApplicationComponent.builder()
            .androidModule(
                AndroidModule(
                    applicationContext
                )
            )
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        component.inject(this)

        initApplication()
    }

    fun initApplication() {
        AndroidUtils.setupTimber()
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

    companion object {
        operator fun get(context: Context): TrainingsApplication {
            return context.applicationContext as TrainingsApplication
        }
    }
}
