package com.example.trainingsapp.app.timer.fragment

interface NewTimerListener {
    fun updateIndividualTimer(minutes: Int, seconds: Int): Int
}
