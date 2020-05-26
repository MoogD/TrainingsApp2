package com.example.trainingsapp.app.main

import android.content.Context
import android.os.Bundle
import com.example.trainingsapp.R
import com.example.trainingsapp.app.base.BaseActivity
import com.example.trainingsapp.injections.annotation.ApplicationContext
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @field :[Inject ApplicationContext]
    internal lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
