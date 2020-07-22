package com.example.trainingsapp.app.timer

import com.example.trainingsapp.app.training.interfaces.Exercise
import com.example.trainingsapp.app.training.interfaces.Training
import com.example.trainingsapp.utils.PreferenceHelper
import com.nhaarman.mockitokotlin2.any
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

class TimerPresenterImpTest {

    private val preferenceHelper: PreferenceHelper = mock(PreferenceHelper::class.java)
    private val presenter: TimerPresenterImp = TimerPresenterImp(preferenceHelper)

    // Values for example pattern
    private val constantPatternOneCount = 3
    private val constantPatternOneStepMinutes = 1
    private val constantPatternOneStepSeconds = 27
    private val constantPatternOneBreakMinutes = 10
    private val constantPatternOneBreakSeconds = 7

    private val individualTimerDurationOne = 20
    private val individualTimerDurationTwo = 150
    private val individualTimerDurationThree = 4

    @Test
    fun `test createConstantTimerPattern for zero minutes and second`() {
        presenter
            .createConstantTimerPattern(
                "Training",
                constantPatternOneCount,
                0,
                0,
                0,
                0
            )
        verify(preferenceHelper, times(0))
            .saveTraining(any())
    }

    @Test
    fun `test createConstantTimerPattern for count is zero`() {
        presenter
            .createConstantTimerPattern(
                "Training",
                0,
                0,
                constantPatternOneStepSeconds,
                0,
                0
            )
        verify(preferenceHelper, times(0))
            .saveTraining(any())
    }

    @Test
    fun `test createConstantTimerPattern for valid steps with minutes and seconds without breaks`() {
        val pattern = provideConstantPatternWithoutBreaksWithMinutesAndSeconds()
        presenter
            .createConstantTimerPattern(
                "Training",
                constantPatternOneCount,
                constantPatternOneStepMinutes,
                constantPatternOneStepSeconds,
                0,
                0
            )
        verify(preferenceHelper, times(1))
            .saveTraining(pattern)
    }

    @Test
    fun `test createConstantTimerPattern for valid steps with minutes and without seconds and breaks`() {
        val pattern = provideConstantPatternWithoutBreaksWithMinutesOnly()
        presenter
            .createConstantTimerPattern(
                "Training",
                constantPatternOneCount,
                constantPatternOneStepMinutes,
                0,
                0,
                0
            )
        verify(preferenceHelper, times(1))
            .saveTraining(pattern)
    }

    @Test
    fun `test createConstantTimerPattern for valid steps with seconds and without minutes and breaks`() {
        val pattern = provideConstantPatternWithoutBreaksWithSecondsOnly()
        presenter
            .createConstantTimerPattern(
                "Training",
                constantPatternOneCount,
                0,
                constantPatternOneStepSeconds,
                0,
                0
            )
        verify(preferenceHelper, times(1))
            .saveTraining(pattern)
    }

    @Test
    fun `test createConstantTimerPattern for valid steps with just non-zero values`() {
        val pattern = provideConstantPatternWithoutZeroValues()
        presenter
            .createConstantTimerPattern(
                "Training",
                constantPatternOneCount,
                constantPatternOneStepMinutes,
                constantPatternOneStepSeconds,
                constantPatternOneBreakMinutes,
                constantPatternOneBreakSeconds
            )
        verify(preferenceHelper, times(1))
            .saveTraining(pattern)
    }

    @Test
    fun `test createConstantTimerPattern for valid steps without break minutes`() {
        val pattern = provideConstantPatternWithoutBreakMinutes()
        presenter
            .createConstantTimerPattern(
                "Training",
                constantPatternOneCount,
                constantPatternOneStepMinutes,
                constantPatternOneStepSeconds,
                0,
                constantPatternOneBreakSeconds
            )
        verify(preferenceHelper, times(1))
            .saveTraining(pattern)
    }

    @Test
    fun `test createConstantTimerPattern for valid steps without break seconds`() {
        val pattern = provideConstantPatternWithoutBreakSeconds()
        presenter
            .createConstantTimerPattern(
                "Training",
                constantPatternOneCount,
                constantPatternOneStepMinutes,
                constantPatternOneStepSeconds,
                constantPatternOneBreakMinutes,
                0
            )
        verify(preferenceHelper, times(1))
            .saveTraining(pattern)
    }

    @Test
    fun `test createIndividualTimerPattern for empty list`() {
        presenter.createIndividualTimerPattern("Training", emptyList())
        verify(preferenceHelper, times(0))
            .saveTraining(any())
    }

    @Test
    fun `test createIndividualTimerPattern for list with only empty Timer`() {
        presenter.createIndividualTimerPattern(
            "Training",
            listOf(
                Exercise.Timer(0),
                Exercise.Timer(0),
                Exercise.Timer(0),
                Exercise.Timer(0)
            )
        )
        verify(preferenceHelper, times(0))
            .saveTraining(any())
    }

    @Test
    fun `test createIndividualTimerPattern for single element list`() {
        val pattern = provideIndivdualTimerPatternWithOneStep()
        presenter.createIndividualTimerPattern(
            "Training",
            listOf(
                Exercise.Timer(
                    individualTimerDurationOne
                )
            )
        )
        verify(preferenceHelper, times(1))
            .saveTraining(pattern)
    }

    @Test
    fun `test createIndividualTimerPattern for multiple element list`() {
        val pattern = provideIndivdualTimerPatternWitMultipleSteps()
        presenter.createIndividualTimerPattern(
            "Training",
            listOf(
                Exercise.Timer(
                    individualTimerDurationOne
                ),
                Exercise.Timer(
                    individualTimerDurationTwo
                ),
                Exercise.Timer(
                    individualTimerDurationThree
                )
            )
        )
        verify(preferenceHelper, times(1))
            .saveTraining(pattern)
    }

    private fun provideConstantPatternWithoutBreaksWithMinutesAndSeconds(): Training {
        val pattern = Training("Training")
        for (i in 0 until constantPatternOneCount) {
            pattern.addExercise(
                Exercise.Timer(
                    constantPatternOneStepMinutes * 60 + constantPatternOneStepSeconds
                )
            )
        }
        return pattern
    }

    private fun provideConstantPatternWithoutBreaksWithSecondsOnly(): Training {
        val pattern = Training("Training")
        for (i in 0 until constantPatternOneCount) {
            pattern.addExercise(
                Exercise.Timer(
                    constantPatternOneStepSeconds
                )
            )
        }
        return pattern
    }

    private fun provideConstantPatternWithoutBreaksWithMinutesOnly(): Training {
        val pattern = Training("Training")
        for (i in 0 until constantPatternOneCount) {
            pattern.addExercise(
                Exercise.Timer(
                    constantPatternOneStepMinutes * 60
                )
            )
        }
        return pattern
    }

    private fun provideConstantPatternWithoutZeroValues(): Training {
        val pattern = Training("Training")
        for (i in 0 until constantPatternOneCount) {
            pattern.addExercise(
                Exercise.Timer(
                    constantPatternOneStepMinutes * 60 + constantPatternOneStepSeconds
                )
            )
            if (i < constantPatternOneCount - 1) {
                pattern.addExercise(
                    Exercise.Timer(
                        constantPatternOneBreakMinutes * 60 +
                                constantPatternOneBreakSeconds
                    )
                )
            }
        }
        return pattern
    }

    private fun provideConstantPatternWithoutBreakSeconds(): Training {
        val pattern = Training("Training")
        for (i in 0 until constantPatternOneCount) {
            pattern.addExercise(
                Exercise.Timer(
                    constantPatternOneStepMinutes * 60 + constantPatternOneStepSeconds
                )
            )
            if (i < constantPatternOneCount - 1) {
                pattern.addExercise(
                    Exercise.Timer(
                        constantPatternOneBreakMinutes * 60
                    )
                )
            }
        }
        return pattern
    }

    private fun provideConstantPatternWithoutBreakMinutes(): Training {
        val pattern = Training("Training")
        for (i in 0 until constantPatternOneCount) {
            pattern.addExercise(
                Exercise.Timer(
                    constantPatternOneStepMinutes * 60 + constantPatternOneStepSeconds
                )
            )
            if (i < constantPatternOneCount - 1) {
                pattern.addExercise(
                    Exercise.Timer(
                        constantPatternOneBreakSeconds
                    )
                )
            }
        }
        return pattern
    }

    private fun provideIndivdualTimerPatternWithOneStep(): Training =
        Training("Training").apply {
            this.addExercise(
                Exercise.Timer(
                    individualTimerDurationOne
                )
            )
        }

    private fun provideIndivdualTimerPatternWitMultipleSteps(): Training =
        Training("Training").apply {
            this.addExercise(
                Exercise.Timer(
                    individualTimerDurationOne
                )
            )
            this.addExercise(
                Exercise.Timer(
                    individualTimerDurationTwo
                )
            )
            this.addExercise(
                Exercise.Timer(
                    individualTimerDurationThree
                )
            )
        }
}
