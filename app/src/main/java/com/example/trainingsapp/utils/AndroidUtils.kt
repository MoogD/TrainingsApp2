package com.example.trainingsapp.utils

import timber.log.Timber

object AndroidUtils {

    private var isTimberPlant = false

    fun setupTimber() {
        if (!isTimberPlant) {
            Timber.plant(Timber.DebugTree())
            isTimberPlant = true
        }
    }
}
