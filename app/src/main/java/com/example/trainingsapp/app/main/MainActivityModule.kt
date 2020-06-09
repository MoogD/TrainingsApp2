package com.example.trainingsapp.app.main

import com.example.trainingsapp.injections.annotation.ActivityScope
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @ActivityScope
    @Provides
    fun providesMainPresenter(): MainContract.Presenter =
        MainPresenterImp()
}
