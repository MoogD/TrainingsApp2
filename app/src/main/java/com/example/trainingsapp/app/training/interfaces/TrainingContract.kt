package com.example.trainingsapp.app.training.interfaces

interface TrainingContract {
    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun requestState()
        fun selectTraining()
        fun showTraining(training: Training)
        fun startTraining(training: Training)
        fun pauseOrResumeTraining()
        fun nextExercise()
        fun onDestroy()
        fun onBackPressed()
        fun getTrainingListener(): TrainingUpdateListener
    }

    interface View {
        fun showState(state: TrainingState)
        fun closeTraining()
        fun updateTimerView(remaining: Int)
        fun showNextTimer(exercise: Exercise.Timer, position: Int)
        fun showNextExercise(exercise: Exercise, position: Int)
        fun stopTraining()
    }
}
