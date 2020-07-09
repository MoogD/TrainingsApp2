package com.example.trainingsapp.app.training.interfaces

interface TrainingUpdateListener {
    fun updateTimer(remaining: Int)
    fun nextExercise(exercise: Exercise, remainingExercises: List<Exercise>)
}