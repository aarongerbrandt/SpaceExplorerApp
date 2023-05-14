package com.example.finalproject.home.carousel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.CarouselLayoutBinding
import com.example.finalproject.apod.Apod

class ApodCarouselAdapter(
    private val apods: List<Apod>
): RecyclerView.Adapter<ApodCarouselHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApodCarouselHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CarouselLayoutBinding.inflate(inflater, parent, false)

        return ApodCarouselHolder(binding)
    }

    override fun getItemCount() = apods.size

    override fun onBindViewHolder(holder: ApodCarouselHolder, position: Int) {
        val response = apods[position]
        holder.bind(response)
    }

}