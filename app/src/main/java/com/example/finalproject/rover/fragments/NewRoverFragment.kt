package com.example.finalproject.rover.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.finalproject.NasaCaller
import com.example.finalproject.databinding.FragmentNewRoverBinding
import com.example.finalproject.rover.RoverListViewModel
import com.example.finalproject.util.DateFormats
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.Calendar
import java.util.Date


class NewRoverFragment : Fragment() {

    private var _binding: FragmentNewRoverBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val roverListViewModel: RoverListViewModel by viewModels()

    private var selectedDate: Date? = null

    private lateinit var api: NasaCaller

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewRoverBinding.inflate(inflater, container, false)

        api = NasaCaller(requireContext())

        setUpDatepicker()
        setUpSubmitButton()

        return binding.root
    }

    private fun addDays(dateInMillis: Long, numDays: Int): Date {
        val c = Calendar.getInstance()
        c.timeInMillis = dateInMillis
        c.add(Calendar.DATE, numDays)
        return c.time
    }

    private fun addDays(date: Date, numDays: Int): Date {
        val c = Calendar.getInstance()
        c.time = date
        c.add(Calendar.DATE, numDays)
        return c.time
    }

    private fun setUpDatepicker() {
        val datePicker = MaterialDatePicker
            .Builder
            .datePicker()
            .setTitleText("Select a date")
            .setCalendarConstraints(
                CalendarConstraints.Builder()
                    .setValidator(
                        DateValidatorPointBackward.now()
                    )
                    .setEnd(MaterialDatePicker.todayInUtcMilliseconds() + 1000)
                    .build()
            )
            .build()

        datePicker.addOnPositiveButtonClickListener { dateMs ->
            selectedDate = addDays(dateMs, 1) // For some reason always 1 day behind
            binding.newRoverDate.text = DateFormats.SIMPLE_OUTPUT_FORMAT.format(selectedDate!!)
            binding.roverSubmitButton.isEnabled = (selectedDate != null)
        }

        datePicker.addOnCancelListener {
            binding.roverSubmitButton.isEnabled = (selectedDate != null)
        }

        datePicker.addOnDismissListener {
            binding.roverSubmitButton.isEnabled = (selectedDate != null)
        }

        datePicker.addOnNegativeButtonClickListener {
            binding.roverSubmitButton.isEnabled = (selectedDate != null)
        }

        binding.newRoverDate.setOnClickListener {
            datePicker.show(
                requireActivity().supportFragmentManager,
                "MATERIAL_DATE_PICKER"
            )
        }
    }

    private fun setUpSubmitButton() {
        binding.roverSubmitButton.setOnClickListener {
            //TODO: Finish rover conversion
//            api.getRover(selectedDate, selectedRover!!, selectedCamera!!, callback = { rovers ->
//                Log.d("NewRoverFrag", rovers.toString())
//                roverListViewModel.addRovers(rovers)
//
//                val vp = requireActivity().findViewById(R.id.view_pager) as ViewPager2
//                vp.setCurrentItem(RoverListFragment.FRAGMENT_NUMBER, false)
//            })
        }
    }

    companion object {
        const val FRAGMENT_NUMBER = 6
    }

}