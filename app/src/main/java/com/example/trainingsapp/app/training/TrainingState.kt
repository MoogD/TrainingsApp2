package com.example.trainingsapp.app.training

interface TrainingState {
    class Select(val trainingList: List<Training>) : TrainingState

    class Start(val training: Training) : TrainingState

    class Stop : TrainingState

    class Show(training: Training) : TrainingState
}