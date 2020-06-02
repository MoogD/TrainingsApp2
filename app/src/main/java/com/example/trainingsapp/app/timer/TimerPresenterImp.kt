package com.example.trainingsapp.app.timer

import com.example.trainingsapp.Utils.PreferenceHelper
import java.lang.NumberFormatException
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
        count: String,
        stepMinutes: String,
        stepSeconds: String,
        breakMinutes: String,
        breakSeconds: String
    ) {
        val timerPattern = TimerPattern()
        if (count.isNullOrBlank() ||
            stepMinutes.isNullOrBlank() ||
            stepSeconds.isNullOrBlank() ||
            breakMinutes.isNullOrBlank() ||
            breakSeconds.isNullOrBlank()
        ) {
            view?.showError("Please enter Numbers in each field!")
        } else {
            try {
                for (i in 0 until Integer.parseInt(count)) {
                    timerPattern.addTimer(
                        TimerPattern.Timer(
                            Integer.parseInt(stepMinutes) * 60 + Integer.parseInt(
                                stepSeconds
                            )
                        )
                    )
                    val minutes = Integer.parseInt(breakMinutes)
                    val seconds = Integer.parseInt(breakSeconds)
                    if (minutes > 0 || seconds > 0) {
                        timerPattern.addTimer(
                            TimerPattern.Timer(minutes * 60 + seconds)
                        )
                    }
                }
                preferenceHelper.saveTimerPattern(timerPattern)
                view?.onTimerCreated()
            } catch(e: NumberFormatException) {
                view?.showError("Please use numbers only!")
            }
        }
    }

    override fun createIndividualTimerPattern(steps: List<Int>) {
        TODO("Not yet implemented")
    }
}