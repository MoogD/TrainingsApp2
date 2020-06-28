package com.example.trainingsapp.app.training

import android.content.Context
import android.os.Bundle
import com.example.trainingsapp.R
import com.example.trainingsapp.app.base.BaseActivity
import com.example.trainingsapp.app.training.fragment.TrainingSelectionFragment
import com.example.trainingsapp.injections.annotation.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

class TrainingActivity : BaseActivity(), TrainingContract.View, TrainingListener {

    @field :[Inject ApplicationContext]
    internal lateinit var context: Context

    @Inject
    internal lateinit var presenter: TrainingContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.training_activity_layout)
        presenter.attachView(this)
        presenter.requestState()
    }

    override fun onDestroy() {
        presenter.detachView()
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun showState(state: TrainingState) {
        Timber.d("Show ${state.javaClass}")
        when (state) {
            is TrainingState.Select -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.add(
                    R.id.container,
                    TrainingSelectionFragment.newInstance(state.trainingList)
                )
                transaction.commit()
            }
            is TrainingState.Show -> {
                Timber.d("Show Training called!")
            }
            is TrainingState.Stop -> {
            }
            is TrainingState.Start -> {
            }
        }
    }

    override fun showTraining(training: Training) {
        presenter.prepareTraining(training)
    }
}