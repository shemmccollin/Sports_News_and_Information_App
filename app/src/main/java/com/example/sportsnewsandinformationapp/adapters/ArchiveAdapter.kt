package com.example.sportsnewsandinformationapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsnewsandinformationapp.R
import com.example.sportsnewsandinformationapp.modals.ArchiveModal
import com.example.sportsnewsandinformationapp.modals.EventsModal

class ArchiveAdapter(private val archives: ArrayList<ArchiveModal>): RecyclerView.Adapter<ArchiveAdapter.ArchiveViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArchiveViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.archive_item, parent, false)
        return ArchiveViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ArchiveViewHolder, position: Int) {

        holder.archiveName.text = archives[position].archiveName
        holder.date.text = archives[position].date
        holder.description.text = archives[position].description
    }

    override fun getItemCount(): Int {
        return archives.size
    }

    class ArchiveViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val archiveName: TextView = itemView.findViewById(R.id.archiveName)
        val date: TextView = itemView.findViewById(R.id.archiveDate)
        val description: TextView = itemView.findViewById(R.id.archiveDescription)
        val context: Context = itemView.context
    }
}