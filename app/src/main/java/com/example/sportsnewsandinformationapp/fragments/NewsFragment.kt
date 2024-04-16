package com.example.sportsnewsandinformationapp.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sportsnewsandinformationapp.R
import com.example.sportsnewsandinformationapp.adapters.NewsAdapter
import com.example.sportsnewsandinformationapp.adapters.SportsAdapter
import com.example.sportsnewsandinformationapp.databinding.FragmentNewsBinding
import com.example.sportsnewsandinformationapp.modals.DBHelper
import com.example.sportsnewsandinformationapp.modals.NewsModal
import com.example.sportsnewsandinformationapp.modals.SportsModal

class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding
    private lateinit var newsRV: RecyclerView
    private lateinit var  newsAdapter: NewsAdapter
    private lateinit var newsList: ArrayList<NewsModal>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_news, container, false)
        binding = FragmentNewsBinding.bind(view)

       newsList = ArrayList()
        newsRV = binding.newsRecycler

        val layoutManager = LinearLayoutManager(context)
        newsRV.layoutManager = layoutManager

        newsAdapter = NewsAdapter(newsList)

        newsRV.adapter = newsAdapter

        retrieveNewsData()

        binding.newsFloatingBtn.setOnClickListener { it ->
            onNewsFloatingBtn(it)
        }

        return binding.root
    }

    private fun onNewsFloatingBtn(view: View) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(getString(R.string.addNew))
        builder.setIcon(R.drawable.news)
        val mView = getLayoutInflater().inflate(R.layout.add_new_news, null)
        val imageUrl = mView.findViewById<EditText>(R.id.newsNewImageUrl)
        val title = mView.findViewById<EditText>(R.id.newsNewTitle)
        val description = mView.findViewById<EditText>(R.id.newsNewDescription)

        builder.setView(mView)
            .setPositiveButton("Add", DialogInterface.OnClickListener{ dialog, id ->
                if(imageUrl.text.isBlank())
                {
                    Toast.makeText(context, "Image Url can not be empty!", Toast.LENGTH_LONG).show()
                }
                else if(title.text.isBlank())
                {
                    Toast.makeText(context, "Title can not be empty!", Toast.LENGTH_LONG).show()
                }
                else if(description.text.isBlank())
                {
                    Toast.makeText(context, "Description can not be empty!", Toast.LENGTH_LONG).show()
                }
                else
                {
                    addNewsData(imageUrl.text.toString(),title.text.toString(), description.text.toString())
                    Toast.makeText(context, "News Added", Toast.LENGTH_LONG).show()
                }
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener{ dialog, _ ->
                dialog.dismiss()
            })
        builder.create()

        builder.show()
    }

    @SuppressLint("Range")
    private fun retrieveNewsData(){

        val db = DBHelper(requireContext(), null)

        val cursor = db.getNews()

        if(cursor!!.count > 0) {
            newsList.clear()
            cursor.moveToFirst()
            var imageUrl = cursor.getString(cursor.getColumnIndex(DBHelper.NEWS_IMAGE_URL_COL))
            var title = cursor.getString(cursor.getColumnIndex(DBHelper.NEWS_TITLE_COL))
            var description = cursor.getString(cursor.getColumnIndex(DBHelper.NEWS_DESCRIPTION_COL))

            newsList.add(NewsModal(imageUrl, title, description))

            while (cursor.moveToNext()) {
                imageUrl = cursor.getString(cursor.getColumnIndex(DBHelper.NEWS_IMAGE_URL_COL))
                title = cursor.getString(cursor.getColumnIndex(DBHelper.NEWS_TITLE_COL))
                description = cursor.getString(cursor.getColumnIndex(DBHelper.NEWS_DESCRIPTION_COL))

                newsList.add(NewsModal(imageUrl, title, description))
            }
            newsAdapter.notifyDataSetChanged()
        }
    }

    private fun addNewsData(imageUrl: String, title: String, description: String){
        val db = DBHelper(requireContext(), null)
        db.addNews(imageUrl, title, description)

        retrieveNewsData()
    }
}