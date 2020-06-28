package com.example.trainingsapp.app.training

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

    override fun prepareTraining(training: Training) {
        currentState = TrainingState.Show(training)
        view?.showState(currentState)
    }

    override fun onDestroy() {
        currentState = TrainingState.Select(trainingList)
    }
}