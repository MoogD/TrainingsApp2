package com.example.trainingsapp.app.training

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.CountDownTimer
import android.os.IBinder
import com.example.trainingsapp.app.training.interfaces.Exercise
import com.example.trainingsapp.app.training.interfaces.Training
import com.example.trainingsapp.app.training.interfaces.TrainingUpdateListener

class TrainingService() : Service() {
    private val binder = TrainingBinder()
    private lateinit var currentExercise: Exercise
    private lateinit var training: Training
    private var listener: TrainingUpdateListener? = null

    private var timer: CountDownTimer? = null
//    private val job = Job()
//    private val scope = CoroutineScope(Dispatchers.Default + job)


    override fun onBind(p0: Intent?): IBinder? = binder

    override fun onUnbind(intent: Intent?): Boolean {
        stopSelf()
        return super.onUnbind(intent)
    }

    fun setListener(trainingUpdateListener: TrainingUpdateListener) {
        listener = trainingUpdateListener
    }

    fun startTraining(training: Training) {
        this.training = training
        nextExercise()
    }

    private  fun nextExercise() {
//        if (scope.isActive) {
//            scope.cancel()
//            timer?.cancel()
//        }
        if (training.exercises.isNotEmpty()) {
            timer?.cancel()
            currentExercise = training.exercises[0]
            training.exercises.removeAt(0)
            listener?.nextExercise(currentExercise, training.exercises)
            when (currentExercise) {
                is Exercise.Timer -> {
                    startTimer(currentExercise.amount * 1000)
                }
            }
        } else {

        }
    }

    private fun startTimer(remainingTime: Int) {
        timer = object : CountDownTimer(remainingTime.toLong(), 1000) {
            override fun onFinish() {
                nextExercise()
            }

            override fun onTick(timeUntilFinished: Long) {
                listener?.updateTimer((timeUntilFinished /1000).toInt())
            }
        }
        timer?.start()
    }

    inner class TrainingBinder : Binder() {
        fun getService(): TrainingService = this@TrainingService
    }
}