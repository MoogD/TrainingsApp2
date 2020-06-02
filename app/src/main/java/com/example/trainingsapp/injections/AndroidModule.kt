package com.example.trainingsapp.injections

import android.content.Context
import com.example.trainingsapp.Utils.PreferenceHelper
import com.example.trainingsapp.Utils.PreferenceHelperImp
import com.example.trainingsapp.injections.annotation.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AndroidModule(val context: Context) {

    @Provides
    @Singleton
    @ApplicationContext
    fun provideContext() = context

    @Provides
    @Singleton
    fun provideResources() = context.resources!!

    @Singleton
    @Provides
    fun providePreferenceHelper(
        @ApplicationContext context: Context
    ): PreferenceHelper = PreferenceHelperImp(context)
}
