package com.example.trainingsapp.injections

import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
    AndroidSupportInjectionModule::class,
    AndroidModule::class,
    TrainingApplicationModule::class,
    ApplicationBuilderModule::class
    ]
)
interface TrainingApplicationComponent {

    fun inject(application: TrainingsApplication)
}
