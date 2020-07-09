package com.example.trainingsapp.app.training

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.trainingsapp.R
import com.example.trainingsapp.app.training.interfaces.Exercise
import com.google.android.material.textview.MaterialTextView

class ExerciseListAdapter : RecyclerView.Adapter<ExerciseListAdapter.ViewHolder>() {

    private var exerciseList: MutableList<Exercise> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun setExercises(list: List<Exercise>) {
        exerciseList = list.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.exercise_listitem, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = exerciseList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameTextView.text = "${position + 1}. ${exerciseList[position].name ?: "Exercise"}"
        holder.amountTextView.text = exerciseList[position].amount.toString()
    }

    class ViewHolder(exerciseViewHolder: View) : RecyclerView.ViewHolder(exerciseViewHolder) {
        val nameTextView: MaterialTextView = itemView.findViewById(R.id.exerciseNameTextView)
        val amountTextView: MaterialTextView = itemView.findViewById(R.id.exerciseAmountTextView)
    }
}