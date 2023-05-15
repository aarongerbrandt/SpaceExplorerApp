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
            0 -> HomeFragment()
            5 -> NewApodFragment()
            1 -> ApodListFragment()
            3 -> ApodDetailFragment()
            6 -> NewRoverFragment()
            2 -> RoverListFragment()
            4 -> RoverDetailFragment()
            else -> HomeFragment()
        }
    }

}