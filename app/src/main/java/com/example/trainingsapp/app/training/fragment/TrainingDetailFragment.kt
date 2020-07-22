package com.example.trainingsapp.app.training.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.trainingsapp.R
import com.example.trainingsapp.app.training.ExerciseListAdapter
import com.example.trainingsapp.app.training.interfaces.Training
import com.example.trainingsapp.app.training.interfaces.TrainingListener
import kotlinx.android.synthetic.main.training_detail_fragment_layout.backButton
import kotlinx.android.synthetic.main.training_detail_fragment_layout.exercisesRecyclerview
import kotlinx.android.synthetic.main.training_detail_fragment_layout.startTrainingButton
import kotlinx.android.synthetic.main.training_detail_fragment_layout.trainingNameTextView
import timber.log.Timber

class TrainingDetailFragment(private val training: Training) : Fragment() {
    private var listener: TrainingListener? = null
    private var exerciseListAdapter = ExerciseListAdapter()

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
    ): View? =
        inflater.inflate(R.layout.training_detail_fragment_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        trainingNameTextView.text = training.name
        exerciseListAdapter.setExercises(training.exercises)
        exercisesRecyclerview.adapter = exerciseListAdapter
        startTrainingButton.setOnClickListener(::startTraining)
        backButton.setOnClickListener(::backPressed)
    }

    private fun startTraining(view: View) {
        Timber.d("$view pressed")
        listener?.startTraining(training)
    }

    private fun backPressed(view: View) {
        Timber.d("$view pressed")
        listener?.backPressed()
    }
}
