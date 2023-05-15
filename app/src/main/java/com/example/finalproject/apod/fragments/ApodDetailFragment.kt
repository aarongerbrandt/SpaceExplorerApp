package com.example.finalproject.apod.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.finalproject.R
import com.example.finalproject.apod.Apod
import com.example.finalproject.databinding.FragmentApodDetailBinding
import com.example.finalproject.util.CurrentEntryData
import com.example.finalproject.util.DateFormats

class ApodDetailFragment : Fragment() {

    private var _binding: FragmentApodDetailBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentApodDetailBinding.inflate(inflater, container, false)

        var apod: Apod? = CurrentEntryData.currentApod

        if(apod != null) {
            loadData(apod)
        }

        loadButton()

        return binding.root
    }

    private fun loadData(apod:Apod) {
        val date = DateFormats.NASA_FORMAT.parse(apod.date)

        binding.apodDisplayHeader.text = apod.title
        binding.apodDisplayDate.text = DateFormats.EXTENDED_SIMPLE_OUTPUT_FORMAT.format(date)

        Glide.with(this)
            .load(apod.hd_url?: apod.url)
            .into(binding.apodDisplayImg)

        binding.apodDisplayCopyright.text = apod.copyright
        binding.apodDisplayServiceVersion.text = apod.service_version

        binding.apodDisplayDescription.text = apod.explanation
    }

    private fun loadButton() {
        val btn = binding.btnApodReturn
        btn.setOnClickListener {
            val vp = requireActivity().findViewById(R.id.view_pager) as ViewPager2
            vp.setCurrentItem(ApodListFragment.FRAGMENT_NUMBER, false)

            CurrentEntryData.currentApod = null
        }
    }

    companion object {
        const val FRAGMENT_NUMBER = 3
    }

}