package com.example.trainingsapp.app.training

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.fragment.app.Fragment
import com.example.trainingsapp.R
import com.example.trainingsapp.app.base.BaseActivity
import com.example.trainingsapp.app.main.MainActivity
import com.example.trainingsapp.app.training.fragment.TrainingActiveFragment
import com.example.trainingsapp.app.training.fragment.TrainingDetailFragment
import com.example.trainingsapp.app.training.fragment.TrainingSelectionFragment
import com.example.trainingsapp.app.training.interfaces.Exercise
import com.example.trainingsapp.app.training.interfaces.Training
import com.example.trainingsapp.app.training.interfaces.TrainingContract
import com.example.trainingsapp.app.training.interfaces.TrainingListener
import com.example.trainingsapp.app.training.interfaces.TrainingState
import com.example.trainingsapp.injections.annotation.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

class TrainingActivity : BaseActivity(), TrainingContract.View,
    TrainingListener {

    @field :[Inject ApplicationContext]
    internal lateinit var context: Context

    @Inject
    internal lateinit var presenter: TrainingContract.Presenter

    private lateinit var service: TrainingService
    private var bound: Boolean = false
    private var currentFragment: Fragment? = null

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName?, serviceBinder: IBinder?) {
            val binder = serviceBinder as TrainingService.TrainingBinder
            service = binder.getService()
            bound = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            bound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.training_activity_layout)
        presenter.attachView(this)
        presenter.requestState()
        if (!bound) {
            Intent(this, TrainingService::class.java).also { intent ->
                bindService(intent, connection, Context.BIND_AUTO_CREATE)
            }
        }
    }

    override fun onDestroy() {
        presenter.detachView()
        presenter.onDestroy()
        removeService()
        super.onDestroy()
    }

    private fun removeService() {
        if (bound) {
            unbindService(connection)
            bound = false
        }
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    override fun showState(state: TrainingState) {
        Timber.d("Show ${state.javaClass}")
        when (state) {
            is TrainingState.Select -> {
                currentFragment = TrainingSelectionFragment(state.trainingList)
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(
                    R.id.container,
                    currentFragment as TrainingSelectionFragment
                )
                transaction.commit()
//                removeService()
            }
            is TrainingState.Show -> {
                currentFragment = TrainingDetailFragment(state.training)
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(
                    R.id.container,
                    currentFragment as TrainingDetailFragment
                )
                transaction.commit()
//                removeService()
            }
            is TrainingState.Pause -> {
                service.pauseTraining()
                val frag = currentFragment
                if (frag is TrainingActiveFragment) {
                    frag.pauseTraining()
                }
            }
            is TrainingState.Start -> {
                val frag = currentFragment
                if (frag !is TrainingActiveFragment) {
                    currentFragment = TrainingActiveFragment(state.training)
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(
                        R.id.container,
                        currentFragment as TrainingActiveFragment
                    )
                    transaction.commit()
                    service.setListener(presenter.getTrainingListener())
                } else {
                    frag.resumeTraining()
                    service.resumeTraining()
                }
            }
        }
    }

    override fun showTraining(training: Training) {
        presenter.showTraining(training)
    }

    override fun startTraining(training: Training) {
        presenter.startTraining(training)
    }

    override fun pauseTraining() {
        presenter.pauseOrResumeTraining()
    }

    override fun backPressed() {
        onBackPressed()
    }

    override fun nextExercise() {
        presenter.nextExercise()
    }

    override fun onActiveTrainingPrepared() {
        service.startTraining()
    }

    override fun closeTraining() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun updateTimerView(remaining: Int) {
        val frag = currentFragment
        if (frag is TrainingActiveFragment) {
            frag.updateTimer(remaining)
        } else {
            Timber.e("Can not update the timer if there is no active training!")
        }
    }

    override fun showNextTimer(exercise: Exercise.Timer, position: Int) {
        service.nextExercise(exercise)
        val frag = currentFragment
        if (frag is TrainingActiveFragment) {
            frag.nextTimer(exercise, position)
        } else {
            Timber.e("Can not update the timer if there is no active training!")
        }
    }

    override fun showNextExercise(exercise: Exercise, position: Int) {
        service.nextExercise(exercise)
        val frag = currentFragment
        if (frag is TrainingActiveFragment) {
            frag.nextExercise(exercise, position)
        } else {
            Timber.e("Can not update the timer if there is no active training!")
        }
    }

    override fun stopTraining() {
        service.stopTraining()
    }
}
