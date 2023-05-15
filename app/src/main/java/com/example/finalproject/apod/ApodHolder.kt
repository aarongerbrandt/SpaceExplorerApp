package com.example.finalproject.apod

import android.app.Activity
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.finalproject.R
import com.example.finalproject.apod.fragments.ApodDetailFragment
import com.example.finalproject.databinding.ListItemApodBinding
import com.example.finalproject.util.CurrentEntryData
import com.example.finalproject.util.DateFormats

class ApodHolder(
    private val binding: ListItemApodBinding,
    private val activity: Activity,
    private val viewModel: ApodListViewModel
): RecyclerView.ViewHolder(binding.root) {
    fun bind(apod: Apod) {
        val date = DateFormats.NASA_FORMAT.parse(apod.date)

        // Add APOD entity's data to Card
        binding.apodTitle.text = apod.title
        binding.apodDate.text = DateFormats.SIMPLE_OUTPUT_FORMAT.format(date!!)
        binding.apodDescription.text = apod.explanation
        Glide.with(itemView.context)
            .load(apod.hd_url ?: apod.url)
            .override(RecyclerView.LayoutParams.MATCH_PARENT)
            .into(binding.apodImageView)

        binding.apodImageView.contentDescription = apod.explanation

        binding.root.setOnClickListener {
            CurrentEntryData.currentApod = apod
            val vp = activity.findViewById(R.id.view_pager) as ViewPager2
            vp.setCurrentItem(ApodDetailFragment.FRAGMENT_NUMBER, false)
        }

        binding.apodDescription.setOnClickListener {
            if(binding.apodDescription.maxLines == 1) {
                binding.apodDescription.maxLines = 1000
            } else {
                binding.apodDescription.maxLines = 1
            }
        }

        binding.root.setOnLongClickListener {
            if(binding.apodImageView.visibility == View.GONE) {
                binding.apodImageView.visibility = View.VISIBLE
                binding.apodDescription.visibility = View.VISIBLE
            } else {
                binding.apodImageView.visibility = View.GONE
                binding.apodDescription.visibility = View.GONE
            }

            true
        }
    }
}