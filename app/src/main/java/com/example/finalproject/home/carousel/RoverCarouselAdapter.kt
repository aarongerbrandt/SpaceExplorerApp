package com.example.finalproject.home.carousel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.CarouselLayoutBinding
import com.example.finalproject.rover.Rover

class RoverCarouselAdapter(
    val rovers: List<Rover>
): RecyclerView.Adapter<RoverCarouselHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoverCarouselHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CarouselLayoutBinding.inflate(inflater, parent, false)

        return RoverCarouselHolder(binding)
    }

    override fun getItemCount() = rovers.size

    override fun onBindViewHolder(holder: RoverCarouselHolder, position: Int) {
        val response = rovers[position]
        holder.bind(response)
    }

}