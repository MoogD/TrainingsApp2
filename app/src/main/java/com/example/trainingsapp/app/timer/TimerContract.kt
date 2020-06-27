package com.example.trainingsapp.app.timer

import com.example.trainingsapp.app.training.Exercise

interface TimerContract {
    interface View {
        fun onTimerCreated()
        fun showError(msg: String)
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun createConstantTimerPattern(
            name: String,
            count: Int,
            stepMinutes: Int,
            stepSeconds: Int,
            breakMinutes: Int,
            breakSeconds: Int
        )

        fun createIndividualTimerPattern(name: String, steps: List<Exercise.Timer>)
        fun updateIndividualTimer(minutes: Int, seconds: Int): Int
    }
}
