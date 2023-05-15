package com.example.finalproject.rover.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentRoverDetailBinding
import com.example.finalproject.rover.Rover
import com.example.finalproject.util.CurrentEntryData
import com.example.finalproject.util.DateFormats

class RoverDetailFragment : Fragment() {
    private var _binding: FragmentRoverDetailBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoverDetailBinding.inflate(inflater, container, false)

        val rover: Rover? = CurrentEntryData.currentRover

        if(rover != null) {
            displayRover(rover)
        }

        loadButton()

        return binding.root
    }

    private fun displayRover(rover: Rover) {
        binding.roverDisplayHeader.text = rover.camera_full_name

        Glide.with(this)
            .load(rover.img_src)
            .into(binding.roverDisplayImg)

        binding.roverDisplaySol.text = rover.sol.toString()
        binding.roverDisplayDate.text = DateFormats.EXTENDED_SIMPLE_OUTPUT_FORMAT.format(rover.earth_date)
        binding.roverDisplayCameraName.text = "${rover.camera_full_name} (${rover.camera_name})"

        binding.roverDisplayName.text = rover.rover_name
        binding.roverDisplayLaunchDate.text = DateFormats.EXTENDED_SIMPLE_OUTPUT_FORMAT.format(rover.rover_launch_date)
        binding.roverDisplayLandingDate.text = DateFormats.EXTENDED_SIMPLE_OUTPUT_FORMAT.format(rover.rover_landing_date)
        binding.roverDisplayStatus.text = rover.rover_status.replaceFirstChar {it.uppercase()}
    }

    private fun loadButton() {
        val btn = binding.btnRoverReturn
        btn.setOnClickListener {
            val vp = requireActivity().findViewById(R.id.view_pager) as ViewPager2
            vp.setCurrentItem(RoverListFragment.FRAGMENT_NUMBER, false)

            CurrentEntryData.currentRover = null
        }
    }

    companion object {
        const val FRAGMENT_NUMBER = 4
    }
}