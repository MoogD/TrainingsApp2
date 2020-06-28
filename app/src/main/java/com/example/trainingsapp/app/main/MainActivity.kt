package com.example.trainingsapp.app.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.trainingsapp.R
import com.example.trainingsapp.app.base.BaseActivity
import com.example.trainingsapp.app.timer.fragment.NewTimerFragment
import com.example.trainingsapp.app.training.TrainingActivity
import com.example.trainingsapp.injections.annotation.ApplicationContext
import kotlinx.android.synthetic.main.activity_main.container
import kotlinx.android.synthetic.main.activity_main.createTimerButton
import kotlinx.android.synthetic.main.activity_main.mainViewContainer
import kotlinx.android.synthetic.main.activity_main.startTrainingButton
import timber.log.Timber
import javax.inject.Inject

class MainActivity : BaseActivity(), FragmentListener, MainContract.View {

    @field :[Inject ApplicationContext]
    internal lateinit var context: Context

    @Inject
    internal lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.attachView(this)
        createTimerButton.setOnClickListener(::createNewTimer)
        startTrainingButton.setOnClickListener(::onTrainingPressed)
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    private fun createNewTimer(view: View) {
        Timber.d("$view clicked")
        mainViewContainer.visibility = View.GONE
        container.visibility = View.VISIBLE
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.container, NewTimerFragment.newInstance())
        transaction.commit()
    }

    override fun onTimerCreated() {
        container.visibility = View.GONE
        mainViewContainer.visibility = View.VISIBLE
    }

    private fun onTrainingPressed(view: View) {
        presenter.onTrainingPressed()
    }

    override fun startTrainingActivity() {
        startActivity(Intent(this, TrainingActivity::class.java))
        finish()
    }
}
