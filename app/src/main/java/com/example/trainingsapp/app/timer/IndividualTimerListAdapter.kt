package com.example.trainingsapp.app.timer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trainingsapp.R

class IndividualTimerListAdapter : RecyclerView.Adapter<IndividualTimerListAdapter.ViewHolder>() {

    var timerList: MutableList<TimerPattern.Timer> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.individual_timer_listitem, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = timerList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemCount.text = (position + 1).toString()
        holder.stepsMinutes.value = timerList[position].duration / 60
        holder.stepsSeconds.value = timerList[position].duration % 60
    }

    class ViewHolder(timerViewHolder: View) : RecyclerView.ViewHolder(timerViewHolder) {
        val itemCount: TextView = itemView.findViewById(R.id.individualTimerItemCount)
        val stepsMinutes: CustomNumberPicker = itemView.findViewById<LinearLayout>(R.id.timerLayout)
            .findViewById(R.id.numpickerMinutes)
        val stepsSeconds: CustomNumberPicker = itemView.findViewById<LinearLayout>(R.id.timerLayout)
            .findViewById(R.id.numpickerSeconds)
    }
}
