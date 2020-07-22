package com.example.trainingsapp.utils

import com.example.trainingsapp.app.training.interfaces.Training

interface PreferenceHelper {
    fun saveTraining(training: Training)
    fun getTrainingList(): List<Training>
}
