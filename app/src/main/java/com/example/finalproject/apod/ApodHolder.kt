package com.example.finalproject.apod

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.databinding.ListItemApodBinding
import com.example.finalproject.util.DateFormats

class ApodHolder(private val binding: ListItemApodBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(apod:Apod) {
        Log.d("apodHolder bind", "Parsing ${apod.date} -> ${DateFormats.NASA_FORMAT.parse(apod.date)}")
        val date = DateFormats.NASA_FORMAT.parse(apod.date)
        // Add APOD entity's data to Card
        binding.apodTitle.text = apod.title
        binding.apodDate.text = DateFormats.SIMPLE_OUTPUT_FORMAT.format(date!!)
        binding.apodDescription.text = apod.explanation
        Glide.with(itemView.context)
            .load(apod.url)
            .override(RecyclerView.LayoutParams.MATCH_PARENT)
            .into(binding.apodImageView)

        binding.apodImageView.contentDescription = apod.explanation

        binding.root.setOnClickListener {
            if(binding.apodDescription.maxLines == 1) {
                binding.apodDescription.maxLines = 1000
            } else {
                binding.apodDescription.maxLines = 1
            }
        }

        binding.root.setOnLongClickListener {
            if(binding.apodDate.visibility == View.GONE) {
                binding.apodDate.visibility = View.VISIBLE
                binding.apodImageView.visibility = View.VISIBLE
                binding.apodDescription.visibility = View.VISIBLE
            } else {
                binding.apodDate.visibility = View.GONE
                binding.apodImageView.visibility = View.GONE
                binding.apodDescription.visibility = View.GONE
            }

            true
        }
    }
}