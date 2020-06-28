package com.example.trainingsapp.app.training.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trainingsapp.R
import com.example.trainingsapp.app.training.Training
import com.example.trainingsapp.app.training.TrainingItemClickListener
import com.example.trainingsapp.app.training.TrainingListAdapter
import com.example.trainingsapp.app.training.TrainingListener
import timber.log.Timber

class TrainingSelectionFragment(
    private val trainingList: List<Training>
) : Fragment(), TrainingItemClickListener {

    private var listener: TrainingListener? = null
    private lateinit var recyclerView: RecyclerView
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
        recyclerView = view.findViewById(R.id.trainingsOverviewRecyclerview)
        recyclerView.adapter = trainingListAdapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)
    }

    override fun onItemClicked(training: Training) {
        Timber.d("Training ${training.name} selected")
        listener?.showTraining(training)
    }

    companion object {
        @JvmStatic
        fun newInstance(trainingList: List<Training>) =
            TrainingSelectionFragment(trainingList)
    }
}
