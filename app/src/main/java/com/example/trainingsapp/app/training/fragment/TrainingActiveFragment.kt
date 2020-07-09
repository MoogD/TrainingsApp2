package com.example.trainingsapp.app.training.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.trainingsapp.R
import com.example.trainingsapp.app.training.ExerciseListAdapter
import com.example.trainingsapp.app.training.interfaces.Exercise
import com.example.trainingsapp.app.training.interfaces.TrainingListener
import com.example.trainingsapp.app.training.interfaces.TrainingUpdateListener
import kotlinx.android.synthetic.main.training_active_fragment_layout.amountLayout
import kotlinx.android.synthetic.main.training_active_fragment_layout.amountTextView
import kotlinx.android.synthetic.main.training_active_fragment_layout.currentExerciseName
import kotlinx.android.synthetic.main.training_active_fragment_layout.exercisesRecyclerview
import kotlinx.android.synthetic.main.training_active_fragment_layout.timerLayout
import kotlinx.android.synthetic.main.training_active_fragment_layout.timerMinutesTextView
import kotlinx.android.synthetic.main.training_active_fragment_layout.timerSecondsTextView
import timber.log.Timber

class TrainingActiveFragment : Fragment(), TrainingUpdateListener {
    private var exerciseListAdapter = ExerciseListAdapter()
    private var listener: TrainingListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is TrainingListener) {
            listener = context
        } else {
            Timber.e("$context has to Implement TrainingListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.training_active_fragment_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exercisesRecyclerview.adapter = exerciseListAdapter
        listener?.onActiveTrainingPrepared()
    }

    override fun updateTimer(remaining: Int) {
        timerMinutesTextView.text = (remaining / 60).toString()
        timerSecondsTextView.text = (remaining % 60).toString()
    }

    override fun nextExercise(exercise: Exercise, remainingExercises: List<Exercise>) {
        currentExerciseName.text = exercise.name
        when (exercise) {
            is Exercise.Timer -> {
                amountLayout.visibility = View.GONE
                timerLayout.visibility = View.VISIBLE
                timerMinutesTextView.text = (exercise.amount / 60).toString()
                timerSecondsTextView.text = (exercise.amount % 60).toString()
            }
            else -> {
                timerLayout.visibility = View.GONE
                amountLayout.visibility = View.VISIBLE
                amountTextView.text = exercise.amount.toString()
            }
        }
        exerciseListAdapter.setExercises(remainingExercises)
    }
}