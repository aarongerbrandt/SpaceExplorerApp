package com.example.finalproject.home.carousel

import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.finalproject.R
import com.example.finalproject.databinding.CarouselLayoutBinding
import com.example.finalproject.rover.Rover
import com.example.finalproject.rover.fragments.RoverDetailFragment
import com.example.finalproject.util.CurrentEntryData

class RoverCarouselHolder(
    private val binding: CarouselLayoutBinding,
    private val activity: Activity
): RecyclerView.ViewHolder(binding.root) {

    fun bind(rover: Rover) {
        Glide.with(itemView.context)
            .load(rover.img_src)
            .into(binding.carouselImageView)

        binding.root.setOnClickListener {
            CurrentEntryData.currentRover = rover
            val vp = activity.findViewById(R.id.view_pager) as ViewPager2
            vp.setCurrentItem(RoverDetailFragment.FRAGMENT_NUMBER, false)
        }
    }

}