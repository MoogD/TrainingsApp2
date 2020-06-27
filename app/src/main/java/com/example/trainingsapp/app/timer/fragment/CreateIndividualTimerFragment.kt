package com.example.trainingsapp.app.timer.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trainingsapp.R
import com.example.trainingsapp.app.timer.CustomNumberPicker
import com.example.trainingsapp.app.timer.IndividualTimerListAdapter
import com.example.trainingsapp.app.training.Exercise
import kotlinx.android.synthetic.main.new_individual_pattern.addTimerStepFAB
import timber.log.Timber

class CreateIndividualTimerFragment : Fragment() {

    private var listener: NewTimerListener? = null

    private lateinit var recyclerView: RecyclerView
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
        recyclerView = view.findViewById(R.id.individualTimerRecyclerView)
        recyclerView.adapter = timerListAdapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)
    }

    private fun addTimerStep(view: View) {
        Timber.d("$view clicked!")
        timerListAdapter.timerList.forEach {
            val pickerLayout = recyclerView[timerListAdapter.timerList.indexOf(it)]
                .findViewById<LinearLayout>(R.id.stepTimerLayout)
            it.amount = listener?.updateIndividualTimer(
                pickerLayout.findViewById<CustomNumberPicker>(R.id.numpickerMinutes).value,
                pickerLayout.findViewById<CustomNumberPicker>(R.id.numpickerSeconds).value
            ) ?: 0
        }
        timerListAdapter.timerList.add(
            Exercise.Timer(
                0
            )
        )
        timerListAdapter.notifyDataSetChanged()
    }

    fun getTimerList(): List<Exercise.Timer> = timerListAdapter.timerList
}
