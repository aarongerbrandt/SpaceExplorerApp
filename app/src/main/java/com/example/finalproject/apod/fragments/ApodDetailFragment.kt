package com.example.finalproject.apod.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.finalproject.apod.Apod
import com.example.finalproject.databinding.FragmentApodDetailBinding
import com.example.finalproject.util.CurrentEntryData

class ApodDetailFragment : Fragment() {

    private var _binding: FragmentApodDetailBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    var apod: Apod? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentApodDetailBinding.inflate(inflater, container, false)

        apod = CurrentEntryData.currentApod
        loadData(apod)

        return binding.root
    }

    private fun loadData(apod:Apod?) {

    }

    companion object {
        val FRAGMENT_NUMBER = 3
    }

}