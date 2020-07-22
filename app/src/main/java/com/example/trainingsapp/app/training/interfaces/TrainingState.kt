package com.example.trainingsapp.app.training.interfaces

interface TrainingState {
    class Select(val trainingList: List<Training>) :
        TrainingState {
        override fun equals(other: Any?): Boolean {
            if (other !is Select || trainingList.size != other.trainingList.size) {
                return false
            }
            for (training in trainingList) {
                if (training != other.trainingList[trainingList.indexOf(training)]) {
                    return false
                }
            }
            return true
        }

        override fun hashCode(): Int {
            return trainingList.sumBy { Training.hashCode() }
        }
    }

    class Start(val training: Training) :
        TrainingState {
        override fun equals(other: Any?): Boolean =
            other is Start && training == other.training

        override fun hashCode(): Int {
            return training.hashCode()
        }
    }

    class Pause :
        TrainingState {
        override fun equals(other: Any?): Boolean =
            other is Pause

        override fun hashCode(): Int {
            return javaClass.hashCode()
        }
    }

    class Show(val training: Training) :
        TrainingState {
        override fun equals(other: Any?): Boolean =
            other is Show && training == other.training

        override fun hashCode(): Int {
            return training.hashCode()
        }
    }
}
