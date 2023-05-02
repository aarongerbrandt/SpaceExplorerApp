package com.example.finalproject.apod

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.databinding.ListItemApodBinding
import java.text.SimpleDateFormat
import java.util.*

class ApodHolder(private val binding: ListItemApodBinding): RecyclerView.ViewHolder(binding.root) {
    private val inputDateFormat = SimpleDateFormat("yyyy-mm-dd", Locale.US)
    private val outputDateFormat = SimpleDateFormat("EEE, MMM d, yy", Locale.US)

    fun bind(apod:Apod) {
        val date = inputDateFormat.parse(apod.date)
        Log.d("ApodHolder", "Binding $apod")
        binding.apodTitle.text = apod.title
        binding.apodDate.text = outputDateFormat.format(date!!)
        binding.apodDescription.text = apod.explanation
        Glide.with(itemView.context)
            .load(apod.url)
            .into(binding.apodImageView)

        binding.apodImageView.contentDescription = apod.explanation

        binding.root.setOnClickListener {
            if(binding.apodDescription.maxLines == 1) {
                binding.apodDescription.maxLines = 50
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