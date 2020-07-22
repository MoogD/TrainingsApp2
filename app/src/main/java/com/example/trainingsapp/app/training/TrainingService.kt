package com.example.trainingsapp.app.training

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.CountDownTimer
import android.os.IBinder
import com.example.trainingsapp.app.training.interfaces.Exercise
import com.example.trainingsapp.app.training.interfaces.TrainingUpdateListener

class TrainingService : Service() {
    private val binder = TrainingBinder()
    private var listener: TrainingUpdateListener? = null

    private var timer: CountDownTimer? = null
    private var remainingTime: Long = 0

    override fun onBind(p0: Intent?): IBinder? = binder

    override fun onUnbind(intent: Intent?): Boolean {
        stopSelf()
        return super.onUnbind(intent)
    }

    fun setListener(trainingUpdateListener: TrainingUpdateListener) {
        listener = trainingUpdateListener
    }

    fun startTraining() {
        listener?.nextExercise()
    }

    fun pauseTraining() {
        timer?.cancel()
    }

    fun resumeTraining() {
        startTimer(remainingTime.toInt())
    }

    fun nextExercise(exercise: Exercise) {
        timer?.cancel()
        when (exercise) {
            is Exercise.Timer -> {
                startTimer(exercise.amount * 1000)
            }
        }
    }

    fun stopTraining() {
        timer?.cancel()
        remainingTime = 0
    }

    private fun startTimer(time: Int) {
        timer = object : CountDownTimer(time.toLong(), 1000) {
            override fun onFinish() {
                listener?.nextExercise()
            }

            override fun onTick(timeUntilFinished: Long) {
                remainingTime = timeUntilFinished
                listener?.updateTimer((timeUntilFinished / 1000).toInt())
            }
        }
        timer?.start()
    }

    inner class TrainingBinder : Binder() {
        fun getService(): TrainingService = this@TrainingService
    }
}
