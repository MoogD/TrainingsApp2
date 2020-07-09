package com.example.trainingsapp.app.training.interfaces

class Training(val name: String?) {
    val exercises = mutableListOf<Exercise>()

    fun isEmpty(): Boolean = exercises.isEmpty()

    fun addExercise(exercise: Exercise) {
        exercises.add(exercise)
    }

    fun getExercise(index: Int): Exercise? {
        if (exercises.lastIndex <= index) {
            return exercises[index]
        }
        return null
    }

    fun getSize(): Int = exercises.size

    override fun equals(other: Any?): Boolean {
        if (other !is Training) {
            return false
        }
        for (i in 0 until exercises.lastIndex) {
            if (this.getExercise(i)?.amount != other.getExercise(i)?.amount) {
                return false
            }
        }
        return this.getSize() == other.getSize()
    }

    override fun hashCode(): Int {
        return exercises.hashCode()
    }

    companion object {
        // Variable names needed for deserialization:
        const val TRAINING_EXERCISELIST_VARIABLE_NAME = "exercises"
        const val TRAINING_NAME_VARIABLE_NAME = "name"
    }
}