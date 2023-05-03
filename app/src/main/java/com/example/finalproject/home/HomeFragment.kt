package com.example.finalproject.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.finalproject.R
import com.example.finalproject.apod.Apod
import com.example.finalproject.apod.ApodListViewModel
import com.example.finalproject.databinding.FragmentHomeBinding
import com.example.finalproject.rover.Rover
import com.example.finalproject.rover.RoverListViewModel
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var entryCount = 0

    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val roverListViewModel: RoverListViewModel by viewModels()
    private val apodListViewModel: ApodListViewModel by viewModels()

    private var apods: List<Apod> = emptyList()
    private var rovers: List<Rover> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        loadBanner()
        loadCarousel()

        return binding.root
    }

    private fun loadBanner() {
        // Count num entries in either database
        viewLifecycleOwner.lifecycleScope.launch {
            apodListViewModel.apods.collect {
                apods = it
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            roverListViewModel.rovers.collect {
                rovers = it
            }
        }
        entryCount = rovers.size + apods.size

        binding.homeBanner.text = when {
            entryCount > 0 -> getString(R.string.home_welcome_back_message)
            else -> getString(R.string.home_welcome_message_first)
        }
    }

    private fun loadCarousel() {

    }

}