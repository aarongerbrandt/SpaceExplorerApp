package com.example.finalproject

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.finalproject.apod.fragments.ApodDetailFragment
import com.example.finalproject.apod.fragments.ApodListFragment
import com.example.finalproject.apod.fragments.NewApodFragment
import com.example.finalproject.home.HomeFragment
import com.example.finalproject.rover.fragments.NewRoverFragment
import com.example.finalproject.rover.fragments.RoverDetailFragment
import com.example.finalproject.rover.fragments.RoverListFragment

class FragmentPagerAdapter(
    activity: FragmentActivity,
    private val itemCount: Int
): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = itemCount

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            // TODO: Organize Rover and APOD fragment numbers
            0 -> HomeFragment()
            1 -> ApodListFragment()
            2 -> RoverListFragment()
            3 -> ApodDetailFragment()
            4 -> RoverDetailFragment()
            5 -> NewApodFragment()
            6 -> NewRoverFragment()
            else -> HomeFragment()
        }
    }

}