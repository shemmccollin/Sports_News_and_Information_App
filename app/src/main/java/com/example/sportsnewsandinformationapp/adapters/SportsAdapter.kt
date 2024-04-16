package com.example.sportsnewsandinformationapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsnewsandinformationapp.R
import com.example.sportsnewsandinformationapp.modals.SportsModal

class SportsAdapter(private val sports: ArrayList<SportsModal>, private val context: Context): RecyclerView.Adapter<SportsAdapter.SportsViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SportsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.sports_item, parent, false)
        return SportsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SportsViewHolder, position: Int) {
        holder.sportsType.text = sports[position].sportsType
        holder.sportName.text = sports[position].sportsName
        holder.instruction.text = sports[position].instruction
    }

    override fun getItemCount(): Int {
        return sports.size
    }

    class SportsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val sportsType: TextView = itemView.findViewById(R.id.sportsType)
        val sportName: TextView = itemView.findViewById(R.id.sportsName)
        val instruction: TextView = itemView.findViewById(R.id.instruction)
    }
}