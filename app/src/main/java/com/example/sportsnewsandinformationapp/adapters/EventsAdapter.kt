package com.example.sportsnewsandinformationapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsnewsandinformationapp.R
import com.example.sportsnewsandinformationapp.modals.EventsModal

class EventsAdapter(private val events: ArrayList<EventsModal>): RecyclerView.Adapter<EventsAdapter.EventsViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.events_item, parent, false)
        return EventsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {

        holder.eventsName.text = events[position].eventName
        holder.date.text = events[position].date
        holder.description.text = events[position].description
    }

    override fun getItemCount(): Int {
        return events.size
    }

    class EventsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val eventsName: TextView = itemView.findViewById(R.id.eventsName)
        val date: TextView = itemView.findViewById(R.id.eventsDate)
        val description: TextView = itemView.findViewById(R.id.eventsDescription)
        val context: Context = itemView.context
    }
}