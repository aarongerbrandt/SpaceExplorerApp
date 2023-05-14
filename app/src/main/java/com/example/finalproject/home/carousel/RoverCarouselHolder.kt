package com.example.finalproject.home.carousel

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.databinding.CarouselLayoutBinding
import com.example.finalproject.rover.Rover

class RoverCarouselHolder(
    private val binding: CarouselLayoutBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(rover: Rover) {
        Glide.with(itemView.context)
            .load(rover.img_src)
            .into(binding.carouselImageView)
    }

}