package com.example.trainingsapp.app.timer

import com.example.trainingsapp.app.training.Exercise
import com.example.trainingsapp.app.training.Training
import com.example.trainingsapp.utils.PreferenceHelper
import javax.inject.Inject

class TimerPresenterImp @Inject constructor(
    private val preferenceHelper: PreferenceHelper
) : TimerContract.Presenter {
    private var view: TimerContract.View? = null

    override fun attachView(view: TimerContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun createConstantTimerPattern(
        name: String,
        count: Int,
        stepMinutes: Int,
        stepSeconds: Int,
        breakMinutes: Int,
        breakSeconds: Int
    ) {
        val timerPattern = Training(name)
        if ((stepMinutes > 0 || stepSeconds > 0) && count > 0) {
            for (i in 0 until count) {
                timerPattern.addExercise(
                    Exercise.Timer(
                        stepMinutes * 60 + stepSeconds
                    )
                )
                if ((breakMinutes > 0 || breakSeconds > 0) && i < count - 1) {
                    timerPattern.addExercise(
                        Exercise.Timer(
                            breakMinutes * 60 + breakSeconds
                        )
                    )
                }
            }
            preferenceHelper.saveTraining(timerPattern)
        }
        view?.onTimerCreated()
    }

    override fun createIndividualTimerPattern(name: String, steps: List<Exercise.Timer>) {
        val pattern = Training(name).apply {
            steps.forEach {
                if (it.amount > 0) {
                    this.addExercise(it)
                }
            }
        }
        if (!pattern.isEmpty()) {
            preferenceHelper.saveTraining(pattern)
        }
        view?.onTimerCreated()
    }

    override fun updateIndividualTimer(minutes: Int, seconds: Int): Int = minutes * 60 + seconds
}
