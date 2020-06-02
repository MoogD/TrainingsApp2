package com.example.trainingsapp.Utils

import android.content.Context
import com.example.trainingsapp.app.timer.TimerPattern
import com.google.gson.Gson
import javax.inject.Inject

class PreferenceHelperImp @Inject constructor(private val context: Context) : PreferenceHelper {

    private val timerPref =
        context.getSharedPreferences(TIMER_PREFERENCES_KEY, Context.MODE_PRIVATE)

    override fun saveTimerPattern(timerPattern: TimerPattern) {
        val timerList = getTimerPatternList()
            .toMutableList()
            .add(timerPattern)
        val timerListString = Gson()
            .toJson(timerList)
        timerPref.edit()
            .putString(TIMER_LIST_KEY, timerListString)
    }

    override fun getTimerPatternList(): List<TimerPattern> {
        val listString = timerPref.getString(TIMER_LIST_KEY, null) ?: return emptyList()
        return Gson().fromJson(listString, listOf<TimerPattern>().javaClass)
    }

    companion object {
        private const val TIMER_PREFERENCES_KEY = "TIMER_PREFERENCES"
        private const val TIMER_LIST_KEY = "TIMER_LIST"
    }
}