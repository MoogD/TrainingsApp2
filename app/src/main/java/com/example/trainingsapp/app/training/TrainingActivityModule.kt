package com.example.trainingsapp.app.training

import com.example.trainingsapp.app.training.interfaces.TrainingContract
import com.example.trainingsapp.injections.annotation.ActivityScope
import com.example.trainingsapp.utils.PreferenceHelper
import dagger.Module
import dagger.Provides

@Module
class TrainingActivityModule {

    @ActivityScope
    @Provides
    fun providesTrainingPresenter(
        preferenceHelper: PreferenceHelper
    ): TrainingContract.Presenter =
        TrainingPresenterImp(preferenceHelper)
}