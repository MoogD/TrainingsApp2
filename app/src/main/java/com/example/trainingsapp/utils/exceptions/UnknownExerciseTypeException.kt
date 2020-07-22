package com.example.trainingsapp.utils.exceptions

class UnknownExerciseTypeException(
    override val message: String = "Unknown kind of excercise"
) : Exception()
