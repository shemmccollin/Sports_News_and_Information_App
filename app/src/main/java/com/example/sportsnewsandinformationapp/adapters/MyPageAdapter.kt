package com.example.sportsnewsandinformationapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.sportsnewsandinformationapp.fragments.AboutMeFragment
import com.example.sportsnewsandinformationapp.fragments.AthletesFragment
import com.example.sportsnewsandinformationapp.fragments.EventsFragment
import com.example.sportsnewsandinformationapp.fragments.HistoricalSportsArchiveFragment
import com.example.sportsnewsandinformationapp.fragments.NewsFragment
import com.example.sportsnewsandinformationapp.fragments.SportsFragment

class MyPageAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity)
{
    override fun getItemCount() = 6

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> SportsFragment()
            1 -> NewsFragment()
            2 -> AthletesFragment()
            3 -> EventsFragment()
            4 -> HistoricalSportsArchiveFragment()
            5 -> AboutMeFragment()
            else -> Fragment()
        }
    }
}