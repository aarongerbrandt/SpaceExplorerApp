package com.example.finalproject.apod.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.finalproject.apod.Apod
import com.example.finalproject.apod.ApodListViewModel
import com.example.finalproject.databinding.FragmentApodDetailBinding

class ApodDetailFragment : Fragment() {

    private var _binding: FragmentApodDetailBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val apodListViewModel: ApodListViewModel by viewModels()

    var apod:Apod? = null
        get() = apodListViewModel.currentApod.value

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentApodDetailBinding.inflate(inflater, container, false)

        loadData(apod)

        return binding.root
    }

    private fun loadData(apod:Apod?) {

    }

    companion object {
        val FRAGMENT_NUMBER = 3
    }

}