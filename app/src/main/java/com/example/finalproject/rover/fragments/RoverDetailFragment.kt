package com.example.finalproject.rover.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.example.finalproject.databinding.FragmentRoverDetailBinding

class RoverDetailFragment(
//    private var rover:Rover
): Fragment() {
    private var _binding: FragmentRoverDetailBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRoverDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            roverTitle.doOnTextChanged { text,_,_,_ ->

            }

            roverEarthDate.apply {
//                text = rover.earth_date.toString()
                isEnabled = false
            }
        }

    }

    companion object {
        const val FRAGMENT_NUMBER = 4
    }
}