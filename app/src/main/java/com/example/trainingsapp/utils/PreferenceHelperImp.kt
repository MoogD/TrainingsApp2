package com.example.trainingsapp.utils

import android.content.Context
import com.example.trainingsapp.app.training.interfaces.Training
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import javax.inject.Inject

class PreferenceHelperImp @Inject constructor(
    context: Context,
    private val trainingDeserializer: TrainingDeserializer
) : PreferenceHelper {

    private val timerPref =
        context.getSharedPreferences(TIMER_PREFERENCES_KEY, Context.MODE_PRIVATE)

    override fun saveTraining(training: Training) {
        val timerList = getTrainingList()
            .toMutableList()
        timerList.add(training)
        val timerListString = Gson()
            .toJson(timerList)
        timerPref.edit()
            .putString(TIMER_LIST_KEY, timerListString)
            .apply()
    }

    override fun getTrainingList(): List<Training> {
        val listString = timerPref.getString(TIMER_LIST_KEY, null) ?: return emptyList()
        val type: Type = object : TypeToken<List<Training?>?>() {}.type
        val gson = GsonBuilder()
            .registerTypeAdapter(Training::class.java, trainingDeserializer)
            .create()
        return gson.fromJson(listString, type)
    }

    companion object {
        private const val TIMER_PREFERENCES_KEY = "TIMER_PREFERENCES"
        private const val TIMER_LIST_KEY = "TIMER_LIST"
    }
}
