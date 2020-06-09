package com.example.trainingsapp.utils

import android.content.Context
import com.example.trainingsapp.app.timer.TimerPattern
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import javax.inject.Inject

class PreferenceHelperImp @Inject constructor(context: Context) : PreferenceHelper {

    private val timerPref =
        context.getSharedPreferences(TIMER_PREFERENCES_KEY, Context.MODE_PRIVATE)

    override fun saveTimerPattern(timerPattern: TimerPattern) {
        val timerList = getTimerPatternList()
            .toMutableList()
        timerList.add(timerPattern)
        val timerListString = Gson()
            .toJson(timerList)
        timerPref.edit()
            .putString(TIMER_LIST_KEY, timerListString)
            .apply()
    }

    override fun getTimerPatternList(): List<TimerPattern> {
        val listString = timerPref.getString(TIMER_LIST_KEY, null) ?: return emptyList()
        val type: Type = object : TypeToken<List<TimerPattern?>?>() {}.type
        return Gson().fromJson<List<TimerPattern>>(listString, type).toList()
    }

    companion object {
        private const val TIMER_PREFERENCES_KEY = "TIMER_PREFERENCES"
        private const val TIMER_LIST_KEY = "TIMER_LIST"
    }
}
