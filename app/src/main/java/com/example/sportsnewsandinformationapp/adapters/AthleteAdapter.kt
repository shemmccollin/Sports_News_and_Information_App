package com.example.sportsnewsandinformationapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sportsnewsandinformationapp.R
import com.example.sportsnewsandinformationapp.modals.AthleteModal
import com.example.sportsnewsandinformationapp.modals.NewsModal

class AthleteAdapter(private val athletes: ArrayList<AthleteModal>): RecyclerView.Adapter<AthleteAdapter.AthleteViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AthleteViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.athletes_item, parent, false)
        return AthleteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AthleteViewHolder, position: Int) {

        holder.athleteName.text = athletes[position].athleteName
        holder.sportName.text = athletes[position].sportName
        holder.country.text = athletes[position].country
        holder.bestPerformance.text = athletes[position].bestPerformance
        holder.medals.text = athletes[position].medals
        holder.facts.text = athletes[position].facts
    }

    override fun getItemCount(): Int {
        return athletes.size
    }

    class AthleteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val athleteName: TextView = itemView.findViewById(R.id.athleteName)
        val sportName: TextView = itemView.findViewById(R.id.athleteSportName)
        val country: TextView = itemView.findViewById(R.id.athleteCountry)
        val bestPerformance: TextView = itemView.findViewById(R.id.athleteBestPerformance)
        val medals: TextView = itemView.findViewById(R.id.athleteMedals)
        val facts: TextView = itemView.findViewById(R.id.athleteFacts)
        val context: Context = itemView.context
    }
}