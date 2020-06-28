package com.example.trainingsapp.app.training

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trainingsapp.R

class TrainingListAdapter : RecyclerView.Adapter<TrainingListAdapter.ViewHolder>() {

    private var trainingList: MutableList<Training> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var listener: TrainingItemClickListener? = null

    fun setList(list: List<Training>) {
        trainingList = list.toMutableList()
        notifyDataSetChanged()
    }

    fun setListener(listener: TrainingItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.training_listitem, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = trainingList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameTextView.text = trainingList[position].name
        holder.itemView.setOnClickListener {
            listener?.onItemClicked(trainingList[position])
        }
    }

    class ViewHolder(trainingViewHolder: View) : RecyclerView.ViewHolder(trainingViewHolder) {
        val nameTextView: TextView = itemView.findViewById(R.id.trainingNameTextView)
    }
}
