package com.example.finalproject.apod.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.finalproject.NasaCaller
import com.example.finalproject.R
import com.example.finalproject.apod.ApodListViewModel
import com.example.finalproject.databinding.FragmentNewApodBinding
import com.example.finalproject.util.DateFormats
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

class NewApodFragment : Fragment() {

    private var _binding: FragmentNewApodBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val apodListViewModel: ApodListViewModel by viewModels()

    private var selectedDate: Date? = null

    private var datePicker: MaterialDatePicker<Long>? = null
    private lateinit var api: NasaCaller


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewApodBinding.inflate(inflater, container, false)

        api = NasaCaller(requireContext())

        setUpDatepicker()
        setUpSubmitButton()

        return binding.root
    }

    @Suppress("SameParameterValue")
    private fun addDays(dateInMillis: Long, numDays: Int): Date {
        val c = Calendar.getInstance()
        c.timeInMillis = dateInMillis
        c.add(Calendar.DATE, numDays)
        return c.time
    }

    private fun setUpDatepicker() {
        val apodStart = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        apodStart.set(1995, 5, 16)

        datePicker = MaterialDatePicker
            .Builder
            .datePicker()
            .setTitleText("Select a date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setCalendarConstraints(
                CalendarConstraints.Builder()
                    .setValidator(
                        DateValidatorPointBackward.now()
                    )
                    .setStart(apodStart.timeInMillis)
                    .setEnd(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()
            )
            .build()

        datePicker!!.addOnPositiveButtonClickListener { dateMs ->
            selectedDate = addDays(dateMs, 1) // For some reason always 1 day behind
            binding.newApodDate.text = DateFormats.SIMPLE_OUTPUT_FORMAT.format(selectedDate!!)
            binding.apodSubmitButton.isEnabled = (selectedDate != null)
        }

        datePicker!!.addOnCancelListener {
            binding.apodSubmitButton.isEnabled = (selectedDate != null)
        }

        datePicker!!.addOnDismissListener {
            binding.apodSubmitButton.isEnabled = (selectedDate != null)
        }

        datePicker!!.addOnNegativeButtonClickListener {
            binding.apodSubmitButton.isEnabled = (selectedDate != null)
        }

        binding.newApodDate.setOnClickListener {
            datePicker?.show(
                requireActivity().supportFragmentManager,
                "MATERIAL_DATE_PICKER"
            )
        }
    }

    private fun setUpSubmitButton() {
        binding.apodSubmitButton.setOnClickListener {
            api.getApod(selectedDate!!,
                callback = { apodResponse ->
                    Log.d("ApodVolleySuccess", "Adding: $apodResponse")
                    apodListViewModel.addApod(apodResponse)

                    val vp = requireActivity().findViewById(R.id.view_pager) as ViewPager2
                    vp.setCurrentItem(ApodListFragment.FRAGMENT_NUMBER, false)
                },
                error_callback = { volleyError ->
                    Log.d("ApodVolleyError", "Got error: ${volleyError.networkResponse.statusCode}")
                    val response = when(volleyError.networkResponse.statusCode) {
                        400 -> "Invalid date! You can only request the current date or earlier."
                        else -> "There was an error retrieving your image. Try again."
                    }
                    Snackbar.make(
                        binding.coordinatorLayout,
                        response,
                        Snackbar.LENGTH_SHORT
                    ).setAction("Retry") {
                        datePicker?.show(
                            requireActivity().supportFragmentManager,
                            "MATERIAL_DATE_PICKER"
                        )
                    }.show()
                })
        }
    }

    companion object {
        const val FRAGMENT_NUMBER = 5
    }

}