package com.example.sportsnewsandinformationapp.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsnewsandinformationapp.R
import com.example.sportsnewsandinformationapp.adapters.AthleteAdapter
import com.example.sportsnewsandinformationapp.adapters.NewsAdapter
import com.example.sportsnewsandinformationapp.databinding.FragmentAthletesBinding
import com.example.sportsnewsandinformationapp.modals.AthleteModal
import com.example.sportsnewsandinformationapp.modals.DBHelper
import com.example.sportsnewsandinformationapp.modals.NewsModal

class AthletesFragment : Fragment() {
   private lateinit var binding: FragmentAthletesBinding
    private lateinit var athletesRV: RecyclerView
    private lateinit var  athletesAdapter: AthleteAdapter
    private lateinit var athletesList: ArrayList<AthleteModal>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_athletes, container, false)
        binding = FragmentAthletesBinding.bind(view)

        athletesList = ArrayList()
        athletesRV = binding.athletesRecycler

        val layoutManager = LinearLayoutManager(context)
        athletesRV.layoutManager = layoutManager

        athletesAdapter = AthleteAdapter(athletesList)

        athletesRV.adapter = athletesAdapter

        retrieveAthletesData()

        binding.athleteFloatingBtn.setOnClickListener { it ->
            onAthletesFloatingBtn(it)
        }

        return binding.root
    }

    private fun onAthletesFloatingBtn(view: View) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(getString(R.string.addAthletes))
        builder.setIcon(R.drawable.athletes)
        val mView = getLayoutInflater().inflate(R.layout.add_new_athletes, null)
        val athleteName = mView.findViewById<EditText>(R.id.athleteNewName)
        val sportName = mView.findViewById<EditText>(R.id.athleteNewSportName)
        val country = mView.findViewById<EditText>(R.id.athleteNewCountry)
        val bestPerformance = mView.findViewById<EditText>(R.id.athleteNewBestPerformance)
        val medals = mView.findViewById<EditText>(R.id.athleteNewMedals)
        val facts = mView.findViewById<EditText>(R.id.athleteNewFacts)
        builder.setView(mView)
            .setPositiveButton("Add", DialogInterface.OnClickListener{ dialog, id ->
                if(athleteName.text.isBlank())
                {
                    Toast.makeText(context, "Athlete Name can not be empty!", Toast.LENGTH_LONG).show()
                }
                else if(sportName.text.isBlank())
                {
                    Toast.makeText(context, "Sport Name can not be empty!", Toast.LENGTH_LONG).show()
                }
                else if(country.text.isBlank())
                {
                    Toast.makeText(context, "Country can not be empty!", Toast.LENGTH_LONG).show()
                }
                else if(bestPerformance.text.isBlank())
                {
                    Toast.makeText(context, "Best Performance can not be empty!", Toast.LENGTH_LONG).show()
                }
                else if(medals.text.isBlank())
                {
                    Toast.makeText(context, "Medals can not be empty!", Toast.LENGTH_LONG).show()
                }
                else if(facts.text.isBlank())
                {
                    Toast.makeText(context, "Facts can not be empty!", Toast.LENGTH_LONG).show()
                }
                else
                {
                    addAthletesData(athleteName.text.toString(),sportName.text.toString(), country.text.toString(), bestPerformance.text.toString(),medals.text.toString(), facts.text.toString())
                    Toast.makeText(context, "Athletes Added", Toast.LENGTH_LONG).show()
                }
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener{ dialog, _ ->
                dialog.dismiss()
            })
        builder.create()

        builder.show()
    }

    @SuppressLint("Range")
    private fun retrieveAthletesData(){
        val db = DBHelper(requireContext(), null)

        val cursor = db.getAthletes()

        if(cursor!!.count > 0) {
            athletesList.clear()
            cursor.moveToFirst()
            var athleteName = cursor.getString(cursor.getColumnIndex(DBHelper.ATHLETES_NAME_COL))
            var sportName = cursor.getString(cursor.getColumnIndex(DBHelper.ATHLETES_SPORT_NAME_COL))
            var country = cursor.getString(cursor.getColumnIndex(DBHelper.ATHLETES_COUNTRY_COL))
            var bestPerformance = cursor.getString(cursor.getColumnIndex(DBHelper.ATHLETES_BEST_PERFORMANCE_COL))
            var medals = cursor.getString(cursor.getColumnIndex(DBHelper.ATHLETES_MEDALS_COL))
            var facts = cursor.getString(cursor.getColumnIndex(DBHelper.ATHLETES_FACTS_COL))

            athletesList.add(AthleteModal(athleteName, sportName, country, bestPerformance, medals, facts))

            while (cursor.moveToNext()) {
                athleteName = cursor.getString(cursor.getColumnIndex(DBHelper.ATHLETES_NAME_COL))
                sportName = cursor.getString(cursor.getColumnIndex(DBHelper.ATHLETES_SPORT_NAME_COL))
                country = cursor.getString(cursor.getColumnIndex(DBHelper.ATHLETES_COUNTRY_COL))
                bestPerformance = cursor.getString(cursor.getColumnIndex(DBHelper.ATHLETES_BEST_PERFORMANCE_COL))
                medals = cursor.getString(cursor.getColumnIndex(DBHelper.ATHLETES_MEDALS_COL))
                facts = cursor.getString(cursor.getColumnIndex(DBHelper.ATHLETES_FACTS_COL))

                athletesList.add(AthleteModal(athleteName, sportName, country, bestPerformance, medals, facts))
            }
            athletesAdapter.notifyDataSetChanged()
        }
    }

    private fun addAthletesData(athleteName: String, sportName: String, country: String, bestPerformance: String, medals: String, facts: String){
        val db = DBHelper(requireContext(), null)
        db.addAthletes(athleteName, sportName, country, bestPerformance, medals, facts)

        retrieveAthletesData()
    }
}