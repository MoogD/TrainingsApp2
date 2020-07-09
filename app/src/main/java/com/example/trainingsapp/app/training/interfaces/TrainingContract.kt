package com.example.trainingsapp.app.training.interfaces

interface TrainingContract {
    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun requestState()
        fun selectTraining()
        fun showTraining(training: Training)
        fun startTraining(training: Training)
        fun onDestroy()
        fun onBackPressed()
    }

    interface View {
        fun showState(state: TrainingState)
        fun closeTraining()
    }
}