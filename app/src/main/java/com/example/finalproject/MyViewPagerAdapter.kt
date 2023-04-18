package com.example.finalproject

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.finalproject.apod.fragments.ApodListFragment
import com.example.finalproject.rover.fragments.RoverListFragment

class MyViewPagerAdapter(activity: FragmentActivity, private val itemCount: Int): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = itemCount

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> HomeFragment()
            1 -> ApodListFragment()
            2 -> RoverListFragment()
            else -> HomeFragment()
        }
    }

}