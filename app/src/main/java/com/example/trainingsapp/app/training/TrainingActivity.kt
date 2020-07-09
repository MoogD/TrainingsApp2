package com.example.trainingsapp.app.training

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import com.example.trainingsapp.R
import com.example.trainingsapp.app.base.BaseActivity
import com.example.trainingsapp.app.main.MainActivity
import com.example.trainingsapp.app.training.fragment.TrainingActiveFragment
import com.example.trainingsapp.app.training.fragment.TrainingDetailFragment
import com.example.trainingsapp.app.training.fragment.TrainingSelectionFragment
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

    private var currentTraining: Training? = null

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
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(
                    R.id.container,
                    TrainingSelectionFragment.newInstance(state.trainingList)
                )
                transaction.commit()
//                removeService()
            }
            is TrainingState.Show -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(
                    R.id.container,
                    TrainingDetailFragment.newInstance(state.training)
                )
                transaction.commit()
//                removeService()
            }
            is TrainingState.Stop -> {
            }
            is TrainingState.Start -> {
                val trainingActiveFragment = TrainingActiveFragment()
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(
                    R.id.container,
                    trainingActiveFragment
                )
                transaction.commit()
                service.setListener(trainingActiveFragment)
                currentTraining = state.training
            }
        }
    }

    override fun showTraining(training: Training) {
        presenter.showTraining(training)
    }

    override fun startTraining(training: Training) {
        presenter.startTraining(training)
    }

    override fun backPressed() {
        onBackPressed()
    }

    override fun onActiveTrainingPrepared() {
        currentTraining?.let {
            service.startTraining(it)
        } ?: presenter.selectTraining()
    }

    override fun onTrainingFinished() {
        currentTraining?.let {
            presenter.showTraining(it)
        } ?: presenter.selectTraining()
    }

    override fun closeTraining() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
