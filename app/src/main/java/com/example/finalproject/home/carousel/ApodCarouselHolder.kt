package com.example.finalproject.home.carousel

import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.finalproject.R
import com.example.finalproject.apod.Apod
import com.example.finalproject.apod.fragments.ApodDetailFragment
import com.example.finalproject.databinding.CarouselLayoutBinding
import com.example.finalproject.util.CurrentEntryData

class ApodCarouselHolder(
    private val binding: CarouselLayoutBinding,
    private val activity: Activity
): RecyclerView.ViewHolder(binding.root) {

    fun bind(apod: Apod) {
        binding.carouselImageView.contentDescription = "APOD Carousel: ${apod.title}"
        Glide.with(itemView.context)
            .load(apod.hd_url ?: apod.url)
            .into(binding.carouselImageView)

        binding.root.setOnClickListener {
            CurrentEntryData.currentApod = apod
            val vp = activity.findViewById(R.id.view_pager) as ViewPager2
            vp.setCurrentItem(ApodDetailFragment.FRAGMENT_NUMBER, false)
        }
    }

}