package com.example.sportsnewsandinformationapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sportsnewsandinformationapp.R
import com.example.sportsnewsandinformationapp.modals.NewsModal

class NewsAdapter(private val news: ArrayList<NewsModal>): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return NewsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        holder.title.text = news[position].title
        holder.description.text = news[position].description
        Glide.with(holder.context).load(news[position].imageUrl).into(holder.imageUrl)
    }

    override fun getItemCount(): Int {
        return news.size
    }

    class NewsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imageUrl: ImageView = itemView.findViewById(R.id.newsImageView)
        val title: TextView = itemView.findViewById(R.id.newsTitle)
        val description: TextView = itemView.findViewById(R.id.newsDescription)
        val context: Context = itemView.context
    }
}