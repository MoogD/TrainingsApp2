package com.example.trainingsapp.app.training.interfaces

interface Exercise {
    var kind: String
    var amount: Int
    var name: String?
    var explanation: String?

    class Timer(
        override var amount: Int,
        override var name: String? = null,
        override var explanation: String? = null
    ) : Exercise {
        override var kind =
            TIMER_KIND
    }

    companion object {
        // Kind of exercise
        const val TIMER_KIND = "TIMER"

        // Variable names for deserialization
        const val EXERCISE_KIND_VARIABLE_NAME = "kind"
        const val EXERCISE_AMOUNT_VARIABLE_NAME = "amount"
        const val EXERCISE_NAME_VARIABLE_NAME = "name"
        const val EXERCISE_EXPLANATION_VARIABLE_NAME = "explanation"
    }
}
