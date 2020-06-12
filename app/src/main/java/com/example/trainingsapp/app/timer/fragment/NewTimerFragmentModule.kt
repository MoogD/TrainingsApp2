package com.example.trainingsapp.app.timer.fragment

import com.example.trainingsapp.app.timer.TimerContract
import com.example.trainingsapp.app.timer.TimerPresenterImp
import com.example.trainingsapp.injections.annotation.FragmentScope
import com.example.trainingsapp.utils.PreferenceHelper
import dagger.Module
import dagger.Provides

@Module
class NewTimerFragmentModule {

    @FragmentScope
    @Provides
    fun providesTimerPresenter(
        preferenceHelper: PreferenceHelper
    ): TimerContract.Presenter =
        TimerPresenterImp(preferenceHelper)
}
