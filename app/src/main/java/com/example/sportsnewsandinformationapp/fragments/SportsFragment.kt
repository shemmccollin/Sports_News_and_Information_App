package com.example.sportsnewsandinformationapp.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsnewsandinformationapp.R
import com.example.sportsnewsandinformationapp.adapters.SportsAdapter
import com.example.sportsnewsandinformationapp.databinding.FragmentSportsBinding
import com.example.sportsnewsandinformationapp.modals.DBHelper
import com.example.sportsnewsandinformationapp.modals.SportsModal


class SportsFragment : Fragment() {
    private lateinit var binding: FragmentSportsBinding
    private lateinit var sportsRV: RecyclerView
    private lateinit var  sportsAdapter: SportsAdapter
    private lateinit var sportsList: ArrayList<SportsModal>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sports, container, false)
        binding = FragmentSportsBinding.bind(view)

        sportsList = ArrayList()
        sportsRV = binding.sportsRecycler

        val layoutManager = GridLayoutManager(context, 2)
        sportsRV.layoutManager = layoutManager

        sportsAdapter = context?.let { SportsAdapter(sportsList, it) }!!

        sportsRV.adapter = sportsAdapter

        retrieveSportsData()

        binding.sportsFloatingBtn.setOnClickListener { it ->
            onSportsFloatingBtn(it)
        }

        return binding.root
    }

    private fun onSportsFloatingBtn(view: View) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(getString(R.string.addSport))
        builder.setIcon(R.drawable.sports)
        val mView = getLayoutInflater().inflate(R.layout.add_new_sports, null)
        val spinner = mView.findViewById<Spinner>(R.id.sportsSpinner)
        val sportsName = mView.findViewById<EditText>(R.id.sportsNewName)
        val instruction = mView.findViewById<EditText>(R.id.sportsNewInstructions)
        val adapter = ArrayAdapter(view.context, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.Categories))
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        builder.setView(mView)
            .setPositiveButton("Add", DialogInterface.OnClickListener{ dialog , id ->
                if(sportsName.text.isBlank())
                {
                    Toast.makeText(context, "Sports Name can not be empty!", Toast.LENGTH_LONG).show()
                }
                else if(instruction.text.isBlank())
                {
                    Toast.makeText(context, "Instructions can not be empty!", Toast.LENGTH_LONG).show()
                }
                else
                {
                    addSportsData(spinner.selectedItem.toString(),sportsName.text.toString(), instruction.text.toString())
                    Toast.makeText(context, "Sports Added", Toast.LENGTH_LONG).show()
                }
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener{dialog, _ ->
                dialog.dismiss()
            })
        builder.create()

        builder.show()
    }

    @SuppressLint("Range")
    private fun retrieveSportsData(){

        val db = DBHelper(requireContext(), null)

        val cursor = db.getSports()

        if(cursor!!.count > 0) {
            sportsList.clear()
            cursor.moveToFirst()
            var sportsType = cursor.getString(cursor.getColumnIndex(DBHelper.SPORTSTYPE_COL))
            var sportsName = cursor.getString(cursor.getColumnIndex(DBHelper.SPORTSNAME_COL))
            var instruction = cursor.getString(cursor.getColumnIndex(DBHelper.INSTRUCTION_COL))

            sportsList.add(SportsModal(sportsType, sportsName, instruction))

            while (cursor.moveToNext()) {
                sportsType = cursor.getString(cursor.getColumnIndex(DBHelper.SPORTSTYPE_COL))
                sportsName = cursor.getString(cursor.getColumnIndex(DBHelper.SPORTSNAME_COL))
                instruction = cursor.getString(cursor.getColumnIndex(DBHelper.INSTRUCTION_COL))

                sportsList.add(SportsModal(sportsType, sportsName, instruction))
            }
            sportsAdapter.notifyDataSetChanged()
        }
    }

    private fun addSportsData(sportType: String, sportName: String, instruction: String){
        val db = DBHelper(requireContext(), null)
        db.addSports(sportType, sportName, instruction)

        retrieveSportsData()
    }
}