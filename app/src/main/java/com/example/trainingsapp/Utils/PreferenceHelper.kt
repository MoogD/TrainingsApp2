package com.example.trainingsapp.Utils

import com.example.trainingsapp.app.timer.TimerPattern

interface PreferenceHelper {
    fun saveTimerPattern(timerPattern: TimerPattern)
    fun getTimerPatternList(): List<TimerPattern>
}
