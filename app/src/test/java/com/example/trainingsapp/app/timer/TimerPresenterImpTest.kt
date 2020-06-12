package com.example.trainingsapp.app.timer

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
                constantPatternOneCount,
                0,
                0,
                0,
                0
            )
        verify(preferenceHelper, times(0))
            .saveTimerPattern(any())
    }

    @Test
    fun `test createConstantTimerPattern for count is zero`() {
        presenter
            .createConstantTimerPattern(
                0,
                0,
                constantPatternOneStepSeconds,
                0,
                0
            )
        verify(preferenceHelper, times(0))
            .saveTimerPattern(any())
    }

    @Test
    fun `test createConstantTimerPattern for valid steps with minutes and seconds without breaks`() {
        val pattern = provideConstantPatternWithoutBreaksWithMinutesAndSeconds()
        presenter
            .createConstantTimerPattern(
                constantPatternOneCount,
                constantPatternOneStepMinutes,
                constantPatternOneStepSeconds,
                0,
                0
            )
        verify(preferenceHelper, times(1))
            .saveTimerPattern(pattern)
    }

    @Test
    fun `test createConstantTimerPattern for valid steps with minutes and without seconds and breaks`() {
        val pattern = provideConstantPatternWithoutBreaksWithMinutesOnly()
        presenter
            .createConstantTimerPattern(
                constantPatternOneCount,
                constantPatternOneStepMinutes,
                0,
                0,
                0
            )
        verify(preferenceHelper, times(1))
            .saveTimerPattern(pattern)
    }

    @Test
    fun `test createConstantTimerPattern for valid steps with seconds and without minutes and breaks`() {
        val pattern = provideConstantPatternWithoutBreaksWithSecondsOnly()
        presenter
            .createConstantTimerPattern(
                constantPatternOneCount,
                0,
                constantPatternOneStepSeconds,
                0,
                0
            )
        verify(preferenceHelper, times(1))
            .saveTimerPattern(pattern)
    }

    @Test
    fun `test createConstantTimerPattern for valid steps with just non-zero values`() {
        val pattern = provideConstantPatternWithoutZeroValues()
        presenter
            .createConstantTimerPattern(
                constantPatternOneCount,
                constantPatternOneStepMinutes,
                constantPatternOneStepSeconds,
                constantPatternOneBreakMinutes,
                constantPatternOneBreakSeconds
            )
        verify(preferenceHelper, times(1))
            .saveTimerPattern(pattern)
    }

    @Test
    fun `test createConstantTimerPattern for valid steps without break minutes`() {
        val pattern = provideConstantPatternWithoutBreakMinutes()
        presenter
            .createConstantTimerPattern(
                constantPatternOneCount,
                constantPatternOneStepMinutes,
                constantPatternOneStepSeconds,
                0,
                constantPatternOneBreakSeconds
            )
        verify(preferenceHelper, times(1))
            .saveTimerPattern(pattern)
    }

    @Test
    fun `test createConstantTimerPattern for valid steps without break seconds`() {
        val pattern = provideConstantPatternWithoutBreakSeconds()
        presenter
            .createConstantTimerPattern(
                constantPatternOneCount,
                constantPatternOneStepMinutes,
                constantPatternOneStepSeconds,
                constantPatternOneBreakMinutes,
                0
            )
        verify(preferenceHelper, times(1))
            .saveTimerPattern(pattern)
    }

    @Test
    fun `test createIndividualTimerPattern for empty list`() {
        presenter.createIndividualTimerPattern(emptyList())
        verify(preferenceHelper, times(0))
            .saveTimerPattern(any())
    }

    @Test
    fun `test createIndividualTimerPattern for list with only empty Timer`() {
        presenter.createIndividualTimerPattern(
            listOf(
                TimerPattern.Timer(0),
                TimerPattern.Timer(0),
                TimerPattern.Timer(0),
                TimerPattern.Timer(0)
            )
        )
        verify(preferenceHelper, times(0))
            .saveTimerPattern(any())
    }

    @Test
    fun `test createIndividualTimerPattern for single element list`() {
        val pattern = provideIndivdualTimerPatternWithOneStep()
        presenter.createIndividualTimerPattern(listOf(TimerPattern.Timer(individualTimerDurationOne)))
        verify(preferenceHelper, times(1))
            .saveTimerPattern(pattern)
    }

    @Test
    fun `test createIndividualTimerPattern for multiple element list`() {
        val pattern = provideIndivdualTimerPatternWitMultipleSteps()
        presenter.createIndividualTimerPattern(
            listOf(
                TimerPattern.Timer(individualTimerDurationOne),
                TimerPattern.Timer(individualTimerDurationTwo),
                TimerPattern.Timer(individualTimerDurationThree)
            )
        )
        verify(preferenceHelper, times(1))
            .saveTimerPattern(pattern)
    }

    private fun provideConstantPatternWithoutBreaksWithMinutesAndSeconds(): TimerPattern {
        val pattern = TimerPattern()
        for (i in 0 until constantPatternOneCount) {
            pattern.addTimer(
                TimerPattern.Timer(
                    constantPatternOneStepMinutes * 60 + constantPatternOneStepSeconds
                )
            )
        }
        return pattern
    }

    private fun provideConstantPatternWithoutBreaksWithSecondsOnly(): TimerPattern {
        val pattern = TimerPattern()
        for (i in 0 until constantPatternOneCount) {
            pattern.addTimer(TimerPattern.Timer(constantPatternOneStepSeconds))
        }
        return pattern
    }

    private fun provideConstantPatternWithoutBreaksWithMinutesOnly(): TimerPattern {
        val pattern = TimerPattern()
        for (i in 0 until constantPatternOneCount) {
            pattern.addTimer(TimerPattern.Timer(constantPatternOneStepMinutes * 60))
        }
        return pattern
    }

    private fun provideConstantPatternWithoutZeroValues(): TimerPattern {
        val pattern = TimerPattern()
        for (i in 0 until constantPatternOneCount) {
            pattern.addTimer(
                TimerPattern.Timer(
                    constantPatternOneStepMinutes * 60 + constantPatternOneStepSeconds
                )
            )
            if (i < constantPatternOneCount - 1) {
                pattern.addTimer(
                    TimerPattern.Timer(
                        constantPatternOneBreakMinutes * 60 +
                                constantPatternOneBreakSeconds
                    )
                )
            }
        }
        return pattern
    }

    private fun provideConstantPatternWithoutBreakSeconds(): TimerPattern {
        val pattern = TimerPattern()
        for (i in 0 until constantPatternOneCount) {
            pattern.addTimer(
                TimerPattern.Timer(
                    constantPatternOneStepMinutes * 60 + constantPatternOneStepSeconds
                )
            )
            if (i < constantPatternOneCount - 1) {
                pattern.addTimer(
                    TimerPattern.Timer(
                        constantPatternOneBreakMinutes * 60
                    )
                )
            }
        }
        return pattern
    }

    private fun provideConstantPatternWithoutBreakMinutes(): TimerPattern {
        val pattern = TimerPattern()
        for (i in 0 until constantPatternOneCount) {
            pattern.addTimer(
                TimerPattern.Timer(
                    constantPatternOneStepMinutes * 60 + constantPatternOneStepSeconds
                )
            )
            if (i < constantPatternOneCount - 1) {
                pattern.addTimer(
                    TimerPattern.Timer(constantPatternOneBreakSeconds)
                )
            }
        }
        return pattern
    }

    private fun provideIndivdualTimerPatternWithOneStep(): TimerPattern =
        TimerPattern().apply { this.addTimer(TimerPattern.Timer(individualTimerDurationOne)) }

    private fun provideIndivdualTimerPatternWitMultipleSteps(): TimerPattern =
        TimerPattern().apply {
            this.addTimer(TimerPattern.Timer(individualTimerDurationOne))
            this.addTimer(TimerPattern.Timer(individualTimerDurationTwo))
            this.addTimer(TimerPattern.Timer(individualTimerDurationThree))
        }
}
