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
import com.example.finalproject.home.carousel.ApodCarouselAdapter
import com.example.finalproject.home.carousel.RoverCarouselAdapter
import com.example.finalproject.rover.Rover
import com.example.finalproject.rover.RoverListViewModel
import com.google.android.material.carousel.CarouselLayoutManager
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val roverListViewModel: RoverListViewModel by viewModels()
    private val apodListViewModel: ApodListViewModel by viewModels()
    private var entryCount = 0

    private val numItemsInCarousel = 10

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
                loadApodCarousel(apodList)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            roverListViewModel.rovers.collect { roverList ->
                loadRoverCarousel(roverList)
            }
        }
    }

    private fun loadApodCarousel(apods: List<Apod>) {
        binding.apodCarouselRecyclerView.isNestedScrollingEnabled = false

        val carouselItems = if(apods.isNotEmpty()) {
            binding.apodCarousel.visibility = View.VISIBLE
            binding.homeBanner.text = getString(R.string.home_welcome_back_message)
            if(apods.size > numItemsInCarousel) apods.shuffled().subList(0, numItemsInCarousel) else apods
        } else {
            binding.apodCarousel.visibility = View.GONE
            emptyList()
        }

        binding.apodCarouselRecyclerView.adapter = ApodCarouselAdapter(carouselItems.shuffled())
        binding.apodCarouselRecyclerView.layoutManager = CarouselLayoutManager()

        entryCount += apods.size
    }

    private fun loadRoverCarousel(rovers: List<Rover>) {
        binding.roverCarouselRecyclerView.isNestedScrollingEnabled = false



        val carouselItems = if(rovers.isNotEmpty()) {
            binding.roverCarousel.visibility = View.VISIBLE
            binding.homeBanner.text = getString(R.string.home_welcome_back_message)
            if (rovers.size > numItemsInCarousel) rovers.shuffled().subList(0, numItemsInCarousel) else rovers
        } else {
            binding.roverCarousel.visibility = View.GONE
            emptyList()
        }

        binding.roverCarouselRecyclerView.adapter = RoverCarouselAdapter(carouselItems.shuffled())
        binding.roverCarouselRecyclerView.layoutManager = CarouselLayoutManager()

        entryCount += rovers.size
    }

}