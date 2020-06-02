package com.example.trainingsapp.injections

import com.example.trainingsapp.app.main.MainActivity
import com.example.trainingsapp.app.main.MainActivityModule
import com.example.trainingsapp.app.timer.fragment.NewTimerFragment
import com.example.trainingsapp.app.timer.fragment.NewTimerFragmentModule
import com.example.trainingsapp.injections.annotation.ActivityScope
import com.example.trainingsapp.injections.annotation.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ApplicationBuilderModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [(MainActivityModule::class)])
    abstract fun bindMainActivity(): MainActivity

    @FragmentScope
    @ContributesAndroidInjector(modules = [(NewTimerFragmentModule::class)])
    abstract fun bindNewTimerFragment(): NewTimerFragment
}
