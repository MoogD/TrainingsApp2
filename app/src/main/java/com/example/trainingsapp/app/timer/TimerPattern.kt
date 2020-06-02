package com.example.trainingsapp.app.timer

class TimerPattern {
    private val pattern = mutableListOf<Timer>()

    fun addTimer(timer: Timer) {
        pattern.add(timer)
    }

    fun deleteTimer(timer: Timer) {
        pattern.remove(timer)
    }

    fun getTimer(position: Int): Timer = pattern[position]

    class Timer(val duration: Int, val name: String? = null)
}