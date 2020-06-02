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
            count: String,
            stepMinutes: String,
            stepSeconds: String,
            breakMinutes: String,
            breakSeconds: String
        )

        fun createIndividualTimerPattern(steps: List<Int>)
    }
}