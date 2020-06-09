package com.example.trainingsapp.app.timer

class TimerPattern {
    private val pattern = mutableListOf<Timer>()

    fun isEmpty(): Boolean = pattern.isEmpty()

    fun getSize(): Int = pattern.size

    fun addTimer(timer: Timer) {
        pattern.add(timer)
    }

    fun deleteTimer(timer: Timer) {
        pattern.remove(timer)
    }

    fun getTimer(position: Int): Timer? {
        if (pattern.lastIndex <= position) {
            return pattern[position]
        }
        return null
    }

    override fun equals(other: Any?): Boolean {
        if (other !is TimerPattern) {
            return false
        }
        for (i in 0 until pattern.lastIndex) {
            if (this.getTimer(i)?.duration != other.getTimer(i)?.duration) {
                return false
            }
        }
        return this.getSize() == other.getSize()
    }

    override fun hashCode(): Int {
        return pattern.hashCode()
    }

    class Timer(var duration: Int, var name: String? = null)
}
