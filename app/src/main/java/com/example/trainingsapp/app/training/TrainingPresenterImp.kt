package com.example.trainingsapp.app.training

import com.example.trainingsapp.app.training.interfaces.Training
import com.example.trainingsapp.app.training.interfaces.TrainingContract
import com.example.trainingsapp.app.training.interfaces.TrainingState
import com.example.trainingsapp.utils.PreferenceHelper
import javax.inject.Inject

class TrainingPresenterImp @Inject constructor(
    private val preferenceHelper: PreferenceHelper
) : TrainingContract.Presenter {
    private var view: TrainingContract.View? = null
    private var trainingList = preferenceHelper.getTrainingList()
    private var currentState: TrainingState =
        TrainingState.Select(trainingList)

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
        currentState = TrainingState.Select(trainingList)
        view?.showState(currentState)
    }

    override fun showTraining(training: Training) {
        currentState = TrainingState.Show(training)
        view?.showState(currentState)
    }

    override fun startTraining(training: Training) {
        currentState = TrainingState.Start(training)
        view?.showState(currentState)
    }

    override fun onBackPressed() {
        when(val state = currentState) {
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
            is TrainingState.Stop -> {}
        }
    }

    override fun onDestroy() {
        currentState = TrainingState.Select(trainingList)
    }
}