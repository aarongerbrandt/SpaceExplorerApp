package com.example.finalproject.apod

import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.databinding.ListItemApodBinding
import java.text.SimpleDateFormat

class ApodHolder(private val binding: ListItemApodBinding): RecyclerView.ViewHolder(binding.root) {
    private val inputDateFormat = SimpleDateFormat("yyyy-mm-dd")
    private val outputDateFormat = SimpleDateFormat("EEE, MMM d, yy")

    fun bind(apod:Apod) {
        val date = inputDateFormat.parse(apod.date)
        Log.d("ApodHolder", "Binding $apod")
        binding.apodTitle.text = apod.title
        binding.apodDate.text = outputDateFormat.format(date!!)
        Glide.with(itemView.context)
            .load(apod.url)
            .into(binding.apodImageView)

        binding.root.setOnClickListener {
            Toast.makeText(
                binding.root.context,
                "${apod.title} clicked!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}