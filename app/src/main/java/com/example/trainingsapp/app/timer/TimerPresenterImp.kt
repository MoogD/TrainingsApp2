package com.example.trainingsapp.app.timer

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
        count: Int,
        stepMinutes: Int,
        stepSeconds: Int,
        breakMinutes: Int,
        breakSeconds: Int
    ) {
        val timerPattern = TimerPattern()
        if ((stepMinutes > 0 || stepSeconds > 0) && count > 0) {
            for (i in 0 until count) {
                timerPattern.addTimer(
                    TimerPattern.Timer(stepMinutes * 60 + stepSeconds)
                )
                if ((breakMinutes > 0 || breakSeconds > 0) && i < count - 1) {
                    timerPattern.addTimer(
                        TimerPattern.Timer(breakMinutes * 60 + breakSeconds)
                    )
                }
            }
            preferenceHelper.saveTimerPattern(timerPattern)
        }
        view?.onTimerCreated()
    }

    override fun createIndividualTimerPattern(steps: List<TimerPattern.Timer>) {
        val pattern = TimerPattern().apply {
            steps.forEach {
                if (it.duration > 0) {
                    this.addTimer(it)
                }
            }
        }
        if (!pattern.isEmpty()) {
            preferenceHelper.saveTimerPattern(pattern)
        }
        view?.onTimerCreated()
    }

    override fun updateIndividualTimer(minutes: Int, seconds: Int): Int = minutes * 60 + seconds
}
