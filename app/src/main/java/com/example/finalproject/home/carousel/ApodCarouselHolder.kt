package com.example.finalproject.home.carousel

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.databinding.CarouselLayoutBinding
import com.example.finalproject.apod.Apod

class ApodCarouselHolder(
    private val binding: CarouselLayoutBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(apod: Apod) {
        Glide.with(itemView.context)
            .load(apod.hd_url ?: apod.url)
            .into(binding.carouselImageView)
    }

}