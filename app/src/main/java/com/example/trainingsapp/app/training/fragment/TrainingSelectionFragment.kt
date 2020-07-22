package com.example.trainingsapp.app.training.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trainingsapp.R
import com.example.trainingsapp.app.training.TrainingListAdapter
import com.example.trainingsapp.app.training.interfaces.Training
import com.example.trainingsapp.app.training.interfaces.TrainingItemClickListener
import com.example.trainingsapp.app.training.interfaces.TrainingListener
import kotlinx.android.synthetic.main.training_selection_fragment_layout.trainingsOverviewRecyclerview
import timber.log.Timber

class TrainingSelectionFragment(
    private val trainingList: List<Training>
) : Fragment(), TrainingItemClickListener {

    private var listener: TrainingListener? = null
    private var trainingListAdapter = TrainingListAdapter()

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
        inflater.inflate(R.layout.training_selection_fragment_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        trainingListAdapter.setList(trainingList)
        trainingListAdapter.setListener(this)
        trainingsOverviewRecyclerview.adapter = trainingListAdapter
        trainingsOverviewRecyclerview.layoutManager = LinearLayoutManager(this.context)
    }

    override fun onItemClicked(training: Training) {
        Timber.d("Training ${training.name} selected")
        listener?.showTraining(training)
    }
}
