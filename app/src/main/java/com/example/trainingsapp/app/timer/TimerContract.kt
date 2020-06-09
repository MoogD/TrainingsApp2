package com.example.trainingsapp.app.timer

interface TimerContract {
    interface View {
        fun onTimerCreated()
        fun showError(msg: String)
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun createConstantTimerPattern(
            count: Int,
            stepMinutes: Int,
            stepSeconds: Int,
            breakMinutes: Int,
            breakSeconds: Int
        )

        fun createIndividualTimerPattern(steps: List<TimerPattern.Timer>)
        fun updateIndividualTimer(minutes: Int, seconds: Int): Int
    }
}
