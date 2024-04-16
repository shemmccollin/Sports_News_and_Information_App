package com.example.sportsnewsandinformationapp.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
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
import com.example.sportsnewsandinformationapp.adapters.EventsAdapter
import com.example.sportsnewsandinformationapp.databinding.FragmentEventsBinding
import com.example.sportsnewsandinformationapp.modals.AthleteModal
import com.example.sportsnewsandinformationapp.modals.DBHelper
import com.example.sportsnewsandinformationapp.modals.EventsModal
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

class EventsFragment : Fragment() {
    private lateinit var binding: FragmentEventsBinding
    private lateinit var eventsRV: RecyclerView
    private lateinit var  eventsAdapter: EventsAdapter
    private lateinit var eventsList: ArrayList<EventsModal>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_events, container, false)
        binding = FragmentEventsBinding.bind(view)

        eventsList = ArrayList()
        eventsRV = binding.eventsRecycler

        val layoutManager = LinearLayoutManager(context)
        eventsRV.layoutManager = layoutManager

        eventsAdapter = EventsAdapter(eventsList)

        eventsRV.adapter = eventsAdapter

        retrieveEventsData()

        binding.eventsFloatingBtn.setOnClickListener { it ->
            onEventsFloatingBtn(it)
        }

        return binding.root
    }
    private fun onEventsFloatingBtn(view: View) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(getString(R.string.addEvents))
        builder.setIcon(R.drawable.events)
        val mView = getLayoutInflater().inflate(R.layout.add_new_events, null)
        val eventsName = mView.findViewById<EditText>(R.id.eventsNewName)
        val date = mView.findViewById<EditText>(R.id.eventsNewDate)
        val description = mView.findViewById<EditText>(R.id.eventsNewDescription)

        date.setOnClickListener{
            val today = Calendar.getInstance()
            val year = today.get(Calendar.YEAR)
            val month = today.get(Calendar.MONTH) + 1
            val day = today.get(Calendar.DAY_OF_MONTH)

            var dPicker = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener{view, myear, mmonth, mday ->
                val formatDate = "$mmonth-$mday-$myear"
                date.setText(formatDate)
            },year, month, day).show()

        }

        builder.setView(mView)
            .setPositiveButton("Add", DialogInterface.OnClickListener{ dialog, id ->
                if(eventsName.text.isBlank())
                {
                    Toast.makeText(context, "Event Name can not be empty!", Toast.LENGTH_LONG).show()
                }
                else if(date.text.isBlank())
                {
                    Toast.makeText(context, "Date can not be empty!", Toast.LENGTH_LONG).show()
                }
                else if(description.text.isBlank())
                {
                    Toast.makeText(context, "Description can not be empty!", Toast.LENGTH_LONG).show()
                }
                else
                {
                    addEventsData(eventsName.text.toString(), date.text.toString(), description.text.toString())
                    Toast.makeText(context, "Event Added", Toast.LENGTH_LONG).show()
                }
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener{ dialog, _ ->
                dialog.dismiss()
            })
        builder.create()

        builder.show()
    }

    @SuppressLint("Range")
    private fun retrieveEventsData(){
        val db = DBHelper(requireContext(), null)

        val cursor = db.getEvents()

        if(cursor!!.count > 0) {
            eventsList.clear()
            cursor.moveToFirst()
            var eventsName = cursor.getString(cursor.getColumnIndex(DBHelper.EVENTS_NAME_COL))
            var date = cursor.getString(cursor.getColumnIndex(DBHelper.EVENTS_DATE_COL))
            var description = cursor.getString(cursor.getColumnIndex(DBHelper.EVENTS_DESCRIPTION_COL))

            eventsList.add(EventsModal(eventsName, date, description))

            while (cursor.moveToNext()) {
                eventsName = cursor.getString(cursor.getColumnIndex(DBHelper.EVENTS_NAME_COL))
                date = cursor.getString(cursor.getColumnIndex(DBHelper.EVENTS_DATE_COL))
                description = cursor.getString(cursor.getColumnIndex(DBHelper.EVENTS_DESCRIPTION_COL))

                eventsList.add(EventsModal(eventsName, date, description))
            }
            eventsAdapter.notifyDataSetChanged()
        }
    }

    private fun addEventsData(eventsName: String, date: String, description : String){
        val db = DBHelper(requireContext(), null)
        db.addEvents(eventsName, date, description)

        retrieveEventsData()
    }
}