package com.example.finalproject.rover.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.example.finalproject.databinding.FragmentRoverDetailBinding
import com.example.finalproject.rover.Rover

class RoverDetailFragment(private var rover:Rover): Fragment() {
    private lateinit var binding:FragmentRoverDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRoverDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            roverTitle.doOnTextChanged { text,_,_,_ ->

            }

            roverEarthDate.apply {
                text = rover.earth_date.toString()
                isEnabled = false
            }
        }

    }
}