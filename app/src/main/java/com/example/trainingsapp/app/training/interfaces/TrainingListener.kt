package com.example.trainingsapp.app.training.interfaces

interface TrainingListener {
    fun showTraining(training: Training)
    fun startTraining(training: Training)
    fun pauseTraining()
    fun nextExercise()
    fun backPressed()
    fun onActiveTrainingPrepared()
}
