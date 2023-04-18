package com.example.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout

private const val Tag = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2:ViewPager2
    private lateinit var myViewPagerAdapter: MyViewPagerAdapter

//    val apodListViewModel: ApodListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupTabs()
    }

    private fun setupTabs() {
        tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        viewPager2 = findViewById<ViewPager2>(R.id.view_pager)
        myViewPagerAdapter = MyViewPagerAdapter(this, 3)
        viewPager2.adapter = myViewPagerAdapter

        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager2.currentItem = tab.position
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}

            override fun onTabUnselected(tab: TabLayout.Tab) {}
        })
    }
}