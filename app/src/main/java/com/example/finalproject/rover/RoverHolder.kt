package com.example.finalproject.rover

import android.app.Activity
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.finalproject.R
import com.example.finalproject.databinding.ListItemRoverBinding
import com.example.finalproject.rover.fragments.RoverDetailFragment
import com.example.finalproject.util.CurrentEntryData
import com.example.finalproject.util.DateFormats

class RoverHolder(
    private val binding: ListItemRoverBinding,
    private val activity: Activity
): RecyclerView.ViewHolder(binding.root) {

    fun bind(rover: Rover) {
        binding.roverCamera.text = rover.camera_full_name
        binding.roverEarthDate.text = DateFormats.SIMPLE_OUTPUT_FORMAT.format(rover.earth_date)
        Glide.with(itemView.context)
            .load(rover.img_src)
            .override(RecyclerView.LayoutParams.MATCH_PARENT)
            .into(binding.roverImageView)

        binding.roverImageView.contentDescription = "Image from ${rover.camera_full_name}"

        binding.root.setOnClickListener {
            CurrentEntryData.currentRover = rover
            val vp = activity.findViewById(R.id.view_pager) as ViewPager2
            vp.setCurrentItem(RoverDetailFragment.FRAGMENT_NUMBER, false)
        }

        binding.root.setOnLongClickListener {
            if(binding.roverImageView.visibility == View.GONE) {
                binding.roverImageView.visibility = View.VISIBLE
            } else {
                binding.roverImageView.visibility = View.GONE
            }

            true
        }
    }
}