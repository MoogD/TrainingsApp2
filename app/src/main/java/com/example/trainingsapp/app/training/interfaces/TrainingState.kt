package com.example.trainingsapp.app.training.interfaces

interface TrainingState {
    class Select(val trainingList: List<Training>) :
        TrainingState

    class Start(val training: Training) :
        TrainingState

    class Stop(val training: Training) :
        TrainingState

    class Show(val training: Training) :
        TrainingState
}