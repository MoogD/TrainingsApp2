package com.example.trainingsapp.injections

import com.example.trainingsapp.app.main.MainActivity
import com.example.trainingsapp.app.main.MainActivityModule
import com.example.trainingsapp.injections.annotation.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ApplicationBuilderModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [(MainActivityModule::class)])
    abstract fun bindMainActivity(): MainActivity
}
