package com.example.finalproject.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.finalproject.R
import com.example.finalproject.apod.Apod
import com.example.finalproject.apod.ApodListViewModel
import com.example.finalproject.databinding.FragmentHomeBinding
import com.example.finalproject.rover.Rover
import com.example.finalproject.rover.RoverListViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val prettyDate = SimpleDateFormat("EEE, MMM d, yy", Locale.US)

    private val roverListViewModel: RoverListViewModel by viewModels()
    private val apodListViewModel: ApodListViewModel by viewModels()

    private var apods: List<Apod> = emptyList()
    private var rovers: List<Rover> = emptyList()
    private var entryCount = 0

    private val numImagesInCarousel = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadElements()
    }

    private fun loadElements() {
        viewLifecycleOwner.lifecycleScope.launch {
            apodListViewModel.apods.collect { apodList ->
                apods = apodList.shuffled()
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            roverListViewModel.rovers.collect { roverList ->
                rovers = roverList.shuffled()
            }
        }.also {
            entryCount = rovers.size + apods.size
            Log.d("HomeFragment", "$entryCount = ${rovers.size} + ${apods.size}")

            loadWelcomeBanner()
            loadCarousel()
        }
    }

    private fun loadWelcomeBanner() {
        // Count num entries in either database
        binding.homeBanner.text = when {
            entryCount > 0 -> getString(R.string.home_welcome_back_message)
            else -> getString(R.string.home_welcome_message_first)
        }
    }

    private fun loadCarousel() {
        for(i in 0 until numImagesInCarousel) {
            if (apods.size > i) {
                Log.d("LoadCarousel", "Loading carousel: ${apods[i]}")
                val apod = apods[i]
                binding.apodTitle.text = apod.title
                binding.apodDate.text = apod.date

                Glide.with(requireContext())
                    .load(apod.url)
                    .into(binding.apodImageView)
                Log.d("HomeFragment","Loaded: ${apod.title}")
            }
        }

    }

}