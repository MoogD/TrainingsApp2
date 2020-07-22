package com.example.trainingsapp.app.training

import com.example.trainingsapp.app.training.interfaces.Exercise
import com.example.trainingsapp.app.training.interfaces.Training
import com.example.trainingsapp.app.training.interfaces.TrainingContract
import com.example.trainingsapp.app.training.interfaces.TrainingState
import com.example.trainingsapp.app.training.interfaces.TrainingUpdateListener
import com.example.trainingsapp.utils.PreferenceHelper
import timber.log.Timber
import javax.inject.Inject

class TrainingPresenterImp @Inject constructor(
    private val preferenceHelper: PreferenceHelper
) : TrainingContract.Presenter {
    private var view: TrainingContract.View? = null
    private var trainingList = preferenceHelper.getTrainingList()
    private var currentState: TrainingState =
        TrainingState.Select(trainingList)
    private var currentTraining: Training? = null
    private var currentPosition = 0

    private val trainingUpdateListener = object : TrainingUpdateListener {
        override fun updateTimer(remaining: Int) {
            view?.updateTimerView(remaining)
        }

        override fun nextExercise() {
            this@TrainingPresenterImp.nextExercise()
        }
    }

    override fun attachView(view: TrainingContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun requestState() {
        view?.showState(currentState)
    }

    override fun selectTraining() {
        trainingList = preferenceHelper.getTrainingList()
        currentState = TrainingState.Select(trainingList)
        view?.showState(currentState)
    }

    override fun showTraining(training: Training) {
        currentState = TrainingState.Show(training)
        view?.showState(currentState)
    }

    override fun startTraining(training: Training) {
        currentState = TrainingState.Start(training)
        currentTraining = training
        view?.showState(currentState)
    }

    override fun pauseOrResumeTraining() {
        if (currentState is TrainingState.Start) {
            currentState = TrainingState.Pause()
            view?.showState(currentState)
        } else {
            currentTraining?.let {
                currentState = TrainingState.Start(it)
                view?.showState(currentState)
            } ?: Timber.e("Can not pause or unpause a training that was not started before")
        }
    }

    override fun nextExercise() {
        currentTraining?.let {
            if (currentPosition <= it.exercises.lastIndex) {
                when (val exercise = it.getExercise(currentPosition)) {
                    is Exercise.Timer -> {
                        view?.showNextTimer(exercise, currentPosition)
                    }
                    else -> {}
                }
                currentPosition++
            } else {
                currentPosition = 0
                view?.stopTraining()
                currentState = TrainingState.Show(it)
                view?.showState(currentState)
            }
        } ?: Timber.e("There is no active training!!")
    }

    override fun onBackPressed() {
        when (val state = currentState) {
            is TrainingState.Select -> {
                view?.closeTraining()
                onDestroy()
            }
            is TrainingState.Show -> {
                selectTraining()
            }
            is TrainingState.Start -> {
                showTraining(state.training)
            }
            is TrainingState.Pause -> {
                currentTraining?.let {
                    showTraining(it)
                }
            }
        }
    }

    override fun getTrainingListener(): TrainingUpdateListener = trainingUpdateListener

    override fun onDestroy() {
        currentState = TrainingState.Select(trainingList)
    }
}
