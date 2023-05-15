package com.example.finalproject.home.carousel

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.apod.Apod
import com.example.finalproject.databinding.CarouselLayoutBinding

class ApodCarouselHolder(
    private val binding: CarouselLayoutBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(apod: Apod) {
        binding.carouselImageView.contentDescription = "APOD Carousel: ${apod.title}"
        Glide.with(itemView.context)
            .load(apod.hd_url ?: apod.url)
            .into(binding.carouselImageView)
    }

}