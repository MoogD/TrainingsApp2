package com.example.trainingsapp.app.timer.fragment

import com.example.trainingsapp.Utils.PreferenceHelper
import com.example.trainingsapp.app.timer.TimerContract
import com.example.trainingsapp.app.timer.TimerPresenterImp
import com.example.trainingsapp.injections.annotation.FragmentScope
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NewTimerFragmentModule {

    @FragmentScope
    @Provides
    fun providesTimerPresenter(
        preferenceHelper: PreferenceHelper
    ): TimerContract.Presenter =
        TimerPresenterImp(preferenceHelper)
}