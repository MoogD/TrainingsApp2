package com.example.trainingsapp.app.main

import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import com.example.trainingsapp.R
import com.example.trainingsapp.app.base.BaseActivity
import com.example.trainingsapp.app.timer.fragment.NewTimerFragment
import com.example.trainingsapp.injections.annotation.ApplicationContext
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject

class MainActivity : BaseActivity(), FragmentListener {

    @field :[Inject ApplicationContext]
    internal lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createTimerButton.setOnClickListener(::createNewTimer)
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
}
