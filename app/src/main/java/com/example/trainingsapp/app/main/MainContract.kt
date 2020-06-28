package com.example.trainingsapp.app.main

interface MainContract {
    interface View {
        fun startTrainingActivity()
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun onTrainingPressed()
    }
}
