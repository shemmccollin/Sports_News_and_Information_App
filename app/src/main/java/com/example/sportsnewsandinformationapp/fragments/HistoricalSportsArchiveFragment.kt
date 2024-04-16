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
import com.example.sportsnewsandinformationapp.adapters.ArchiveAdapter
import com.example.sportsnewsandinformationapp.adapters.EventsAdapter
import com.example.sportsnewsandinformationapp.databinding.FragmentHistoricalSportsArchiveBinding
import com.example.sportsnewsandinformationapp.modals.ArchiveModal
import com.example.sportsnewsandinformationapp.modals.DBHelper
import com.example.sportsnewsandinformationapp.modals.EventsModal
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

class HistoricalSportsArchiveFragment : Fragment() {
    private lateinit var binding: FragmentHistoricalSportsArchiveBinding

    private lateinit var archiveRV: RecyclerView
    private lateinit var  archiveAdapter: ArchiveAdapter
    private lateinit var archiveList: ArrayList<ArchiveModal>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_historical_sports_archive, container, false)
        binding = FragmentHistoricalSportsArchiveBinding.bind(view)

        archiveList = ArrayList()
        archiveRV = binding.archiveRecycler

        val layoutManager = LinearLayoutManager(context)
        archiveRV.layoutManager = layoutManager

        archiveAdapter = ArchiveAdapter(archiveList)

        archiveRV.adapter = archiveAdapter

        retrieveArchiveData()

        binding.archiveFloatingBtn.setOnClickListener { it ->
            onArchiveFloatingBtn(it)
        }
        return binding.root
    }

    private fun onArchiveFloatingBtn(view: View) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(getString(R.string.addArchive))
        builder.setIcon(R.drawable.archive)
        val mView = getLayoutInflater().inflate(R.layout.add_new_archive, null)
        val archiveName = mView.findViewById<EditText>(R.id.archiveNewName)
        val date = mView.findViewById<EditText>(R.id.archiveNewDate)
        val description = mView.findViewById<EditText>(R.id.archiveNewDescription)

        date.setOnClickListener{
            val today = Calendar.getInstance()
            val year = today.get(Calendar.YEAR)
            val month = today.get(Calendar.MONTH) + 1
            val day = today.get(Calendar.DAY_OF_MONTH)

            var dPicker = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener{ view, myear, mmonth, mday ->
                val formatDate = "$mmonth-$mday-$myear"
                date.setText(formatDate)
            },year, month, day).show()

        }

        builder.setView(mView)
            .setPositiveButton("Add", DialogInterface.OnClickListener{ dialog, id ->
                if(archiveName.text.isBlank())
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
                    addArchiveData(archiveName.text.toString(), date.text.toString(), description.text.toString())
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
    private fun retrieveArchiveData(){
        val db = DBHelper(requireContext(), null)

        val cursor = db.getArchives()

        if(cursor!!.count > 0) {
            archiveList.clear()
            cursor.moveToFirst()
            var archiveName = cursor.getString(cursor.getColumnIndex(DBHelper.ARCHIVE_NAME_COL))
            var date = cursor.getString(cursor.getColumnIndex(DBHelper.ARCHIVE_DATE_COL))
            var description = cursor.getString(cursor.getColumnIndex(DBHelper.ARCHIVE_DESCRIPTION_COL))

            archiveList.add(ArchiveModal(archiveName, date, description))

            while (cursor.moveToNext()) {
                var archiveName = cursor.getString(cursor.getColumnIndex(DBHelper.ARCHIVE_NAME_COL))
                var date = cursor.getString(cursor.getColumnIndex(DBHelper.ARCHIVE_DATE_COL))
                var description = cursor.getString(cursor.getColumnIndex(DBHelper.ARCHIVE_DESCRIPTION_COL))

                archiveList.add(ArchiveModal(archiveName, date, description))
            }
            archiveAdapter.notifyDataSetChanged()
        }
    }

    private fun addArchiveData(archiveName: String, date: String, description : String){
        val db = DBHelper(requireContext(), null)
        db.addArchive(archiveName, date, description)

        retrieveArchiveData()
    }
}