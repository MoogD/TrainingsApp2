package com.example.trainingsapp.app.training

import com.example.trainingsapp.app.training.interfaces.Exercise
import com.example.trainingsapp.app.training.interfaces.Training
import com.example.trainingsapp.app.training.interfaces.TrainingContract
import com.example.trainingsapp.app.training.interfaces.TrainingState
import com.example.trainingsapp.utils.PreferenceHelper
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class TrainingPresenterImpTest {

    private val preferenceHelper: PreferenceHelper = mock(PreferenceHelper::class.java)
    private val view: TrainingContract.View = mock(TrainingContract.View::class.java)

    private lateinit var trainingPresenter: TrainingPresenterImp
    private val trainingOne: Training = Training("Test").apply {
        this.addExercise(
            Exercise.Timer(
                30,
                "test1",
                "something"
            )
        )
        this.addExercise(
            Exercise.Timer(
                45
            )
        )
    }
    private val trainingTwo: Training = Training("Test").apply {
        this.addExercise(
            Exercise.Timer(
                10,
                "test1",
                "something"
            )
        )
        this.addExercise(
            Exercise.Timer(
                10
            )
        )
        this.addExercise(
            Exercise.Timer(
                10
            )
        )
    }
    private val emptyTraining: Training = Training("Training")

    private val trainingList: List<Training> = listOf(
        trainingOne,
        emptyTraining,
        trainingTwo
    )

    @Before
    fun setup() {
        whenever(preferenceHelper.getTrainingList()).doReturn(trainingList)
        trainingPresenter = TrainingPresenterImp(preferenceHelper)
        trainingPresenter.attachView(view)
    }

    @Test
    fun `test requestState startup`() {
        trainingPresenter.requestState()
        verify(view, times(1)).showState(TrainingState.Select(trainingList))
    }

    @Test
    fun `test selectTraining`() {
        trainingPresenter.selectTraining()
        verify(view, times(1)).showState(TrainingState.Select(trainingList))
        trainingPresenter.requestState()
        verify(view, times(2)).showState(TrainingState.Select(trainingList))
    }

    @Test
    fun `test selectTraining after state changed`() {
        trainingPresenter.showTraining(trainingOne)
        trainingPresenter.requestState()
        verify(view, times(0)).showState(TrainingState.Select(trainingList))
        trainingPresenter.selectTraining()
        verify(view, times(1)).showState(TrainingState.Select(trainingList))
        trainingPresenter.requestState()
        verify(view, times(2)).showState(TrainingState.Select(trainingList))
    }

    @Test
    fun `test showTraining`() {
        trainingPresenter.showTraining(trainingTwo)
        verify(view, times(1)).showState(TrainingState.Show(trainingTwo))
        trainingPresenter.requestState()
        verify(view, times(2)).showState(TrainingState.Show(trainingTwo))
    }

    @Test
    fun `test showTraining after state changed`() {
        trainingPresenter.startTraining(trainingOne)
        verify(view, times(1)).showState(TrainingState.Start(trainingOne))
        trainingPresenter.requestState()
        verify(view, times(2)).showState(TrainingState.Start(trainingOne))
        trainingPresenter.showTraining(trainingOne)
        verify(view, times(1)).showState(TrainingState.Show(trainingOne))
        trainingPresenter.requestState()
        verify(view, times(2)).showState(TrainingState.Show(trainingOne))
    }

    @Test
    fun `test startTraining`() {
        trainingPresenter.startTraining(trainingTwo)
        verify(view, times(1)).showState(TrainingState.Start(trainingTwo))
        trainingPresenter.requestState()
        verify(view, times(2)).showState(TrainingState.Start(trainingTwo))
    }

    @Test
    fun `test startTraining after state changed`() {
        trainingPresenter.showTraining(trainingOne)
        verify(view, times(1)).showState(TrainingState.Show(trainingOne))
        trainingPresenter.requestState()
        verify(view, times(2)).showState(TrainingState.Show(trainingOne))
        trainingPresenter.startTraining(trainingOne)
        verify(view, times(1)).showState(TrainingState.Start(trainingOne))
        trainingPresenter.requestState()
        verify(view, times(2)).showState(TrainingState.Start(trainingOne))
    }

    @Test
    fun `test pauseTraining`() {
        trainingPresenter.startTraining(trainingTwo)
        trainingPresenter.pauseOrResumeTraining()
        verify(view, times(1)).showState(TrainingState.Pause())
        trainingPresenter.requestState()
        verify(view, times(2)).showState(TrainingState.Pause())
    }

    @Test
    fun `test pauseTraining to resume training`() {
        trainingPresenter.startTraining(trainingOne)
        verify(view, times(1)).showState(TrainingState.Start(trainingOne))
        trainingPresenter.pauseOrResumeTraining()
        verify(view, times(1)).showState(TrainingState.Pause())
        trainingPresenter.requestState()
        verify(view, times(2)).showState(TrainingState.Pause())
        trainingPresenter.pauseOrResumeTraining()
        verify(view, times(2)).showState(TrainingState.Start(trainingOne))
        trainingPresenter.requestState()
        verify(view, times(3)).showState(TrainingState.Start(trainingOne))
    }

    @Test
    fun `test nextExercise`() {
        trainingPresenter.startTraining(trainingOne)
        verify(view, times(1)).showState(TrainingState.Start(trainingOne))
        for (i in 0 until trainingOne.getSize()) {
            trainingPresenter.nextExercise()
            verify(view, times(1))
                .showNextTimer(trainingOne.getExercise(i) as Exercise.Timer, i)
        }
        trainingPresenter.nextExercise()
        verify(view, times(1)).stopTraining()
        verify(view, times(1)).showState(TrainingState.Show(trainingOne))
    }

    @Test
    fun `test onBackPressed for Select State`() {
        trainingPresenter.selectTraining()
        trainingPresenter.onBackPressed()
        verify(view, times(1)).closeTraining()
    }

    @Test
    fun `test onBackPressed for Show State`() {
        trainingPresenter.showTraining(trainingOne)
        trainingPresenter.onBackPressed()
        verify(view, times(1)).showState(TrainingState.Select(trainingList))
    }

    @Test
    fun `test onBackPressed for Start State`() {
        trainingPresenter.startTraining(trainingOne)
        trainingPresenter.onBackPressed()
        verify(view, times(1)).showState(TrainingState.Show(trainingOne))
    }

    @Test
    fun `test onBackPressed for Pause State`() {
        trainingPresenter.startTraining(trainingOne)
        trainingPresenter.pauseOrResumeTraining()
        trainingPresenter.onBackPressed()
        verify(view, times(1)).showState(TrainingState.Show(trainingOne))
    }

    @Test
    fun `test trainingListener updateTimer`() {
        val listener = trainingPresenter.getTrainingListener()
        val remaining = 10
        listener.updateTimer(remaining)
        verify(view, times(1)).updateTimerView(remaining)
    }

    @Test
    fun `test trainingListener nextExercise`() {
        val listener = trainingPresenter.getTrainingListener()
        trainingPresenter.startTraining(trainingOne)
        listener.nextExercise()
        verify(view, times(1))
            .showNextTimer(trainingOne.getExercise(0) as Exercise.Timer, 0)
    }
}
