package com.example.trainingsapp.utils

import com.example.trainingsapp.app.training.interfaces.Exercise
import com.example.trainingsapp.app.training.interfaces.Exercise.Companion.EXERCISE_AMOUNT_VARIABLE_NAME
import com.example.trainingsapp.app.training.interfaces.Exercise.Companion.EXERCISE_EXPLANATION_VARIABLE_NAME
import com.example.trainingsapp.app.training.interfaces.Exercise.Companion.EXERCISE_KIND_VARIABLE_NAME
import com.example.trainingsapp.app.training.interfaces.Exercise.Companion.EXERCISE_NAME_VARIABLE_NAME
import com.example.trainingsapp.app.training.interfaces.Exercise.Companion.TIMER_KIND
import com.example.trainingsapp.app.training.interfaces.Training
import com.example.trainingsapp.app.training.interfaces.Training.Companion.TRAINING_EXERCISELIST_VARIABLE_NAME
import com.example.trainingsapp.app.training.interfaces.Training.Companion.TRAINING_NAME_VARIABLE_NAME
import com.example.trainingsapp.utils.exceptions.UnknownExerciseTypeException
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import timber.log.Timber
import java.lang.reflect.Type

object TrainingDeserializer : JsonDeserializer<Training> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Training {
        val jsonObject = json?.asJsonObject
        val training = Training(
            jsonObject?.get(TRAINING_NAME_VARIABLE_NAME)?.asString
        )
        val exercises = jsonObject?.getAsJsonArray(TRAINING_EXERCISELIST_VARIABLE_NAME)
        exercises?.forEach {
            try {
                training.addExercise(
                    deserializeExercise(
                        it.asJsonObject
                    )
                )
            } catch (e: UnknownExerciseTypeException) {
                Timber.w(e)
            }

        }
        return training
    }

    private fun deserializeExercise(json: JsonObject): Exercise {
        when (json.get(EXERCISE_KIND_VARIABLE_NAME).asString) {
            TIMER_KIND -> {
                return Exercise.Timer(
                    json.get(EXERCISE_AMOUNT_VARIABLE_NAME).asInt,
                    json.get(EXERCISE_NAME_VARIABLE_NAME)?.asString,
                    json.get(EXERCISE_EXPLANATION_VARIABLE_NAME)?.asString
                )
            }
            else -> {
                throw UnknownExerciseTypeException()
            }
        }
    }
}