package com.example.trainingsapp.app.training

interface TrainingContract {
    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun requestState()
        fun prepareTraining(training: Training)
        fun onDestroy()
    }

    interface View {
        fun showState(state: TrainingState)
    }
}