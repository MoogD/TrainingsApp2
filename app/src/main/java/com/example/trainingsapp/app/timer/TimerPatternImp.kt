package com.example.trainingsapp.app.timer

class TimerPatternImp : TimerPattern {
    private val pattern = mutableListOf<Timer>()

    override fun addTimer(timer: Timer) {
        pattern.add(timer)
    }

    override fun deleteTimer(timer: Timer) {
        pattern.remove(timer)
    }
}