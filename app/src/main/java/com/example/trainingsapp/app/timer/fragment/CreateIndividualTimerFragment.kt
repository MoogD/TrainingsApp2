package com.example.trainingsapp.app.timer.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trainingsapp.R
import com.example.trainingsapp.app.timer.IndividualTimerListAdapter
import com.example.trainingsapp.app.training.interfaces.Exercise
import kotlinx.android.synthetic.main.new_individual_pattern.addTimerStepFAB
import kotlinx.android.synthetic.main.new_individual_pattern.individualTimerRecyclerView
import timber.log.Timber

class CreateIndividualTimerFragment : Fragment() {

    private var listener: NewTimerListener? = null

    private var timerListAdapter = IndividualTimerListAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NewTimerListener) {
            listener = context
        } else {
            Timber.e("$context has to Implement NewTimerListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.new_individual_pattern, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addTimerStepFAB.setOnClickListener(::addTimerStep)
        individualTimerRecyclerView.adapter = timerListAdapter
        individualTimerRecyclerView.layoutManager = LinearLayoutManager(this.context)
    }

    private fun addTimerStep(view: View) {
        Timber.d("$view clicked!")
        timerListAdapter.timerList.add(
            Exercise.Timer(
                0
            )
        )
        timerListAdapter.notifyItemInserted(timerListAdapter.timerList.lastIndex)
        individualTimerRecyclerView.scrollToPosition(timerListAdapter.itemCount - 1)
    }

    fun getTimerList(): List<Exercise.Timer> = timerListAdapter.timerList
}
