package com.example.finalproject.rover.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.finalproject.NasaCaller
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentNewRoverBinding
import com.example.finalproject.rover.RoverListViewModel
import com.example.finalproject.util.DateFormats
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.Calendar
import java.util.Date


class NewRoverFragment : Fragment() {

    private var _binding: FragmentNewRoverBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val roverListViewModel: RoverListViewModel by viewModels()

    private var selectedRoverIndex: Int = -1
    private var selectedRover: String? = null
        set(value) {
            binding.newRoverRover.text = value ?: getString(R.string.select_rover_button)
            field = value
        }

    private var selectedCameraIndex: Int = -1
    private var selectedCamera: String? = null
        set(value) {
            binding.newRoverCamera.text = value ?: getString(R.string.select_camera_button)
            field = value
        }

    private var selectedDate: Date? = null

    private lateinit var api: NasaCaller

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewRoverBinding.inflate(inflater, container, false)

        api = NasaCaller(requireContext())

        setUpRoverButton()
        setUpRoverCameraButton()
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

    private fun setUpRoverButton() {
        val roverOptions = resources.getStringArray(R.array.rover_options)

        val roverDialog = MaterialAlertDialogBuilder(
            requireContext(),

        )
            .setTitle(R.string.rover_prompt)
            .setCancelable(true)
            .setOnCancelListener {
                selectedRover = null
                selectedCamera = null

                selectedRoverIndex = -1
                selectedCameraIndex = -1
            }

        binding.newRoverRover.setOnClickListener {
            roverDialog
                .setSingleChoiceItems(roverOptions, selectedRoverIndex)
                { dialog, item ->
                    selectedRoverIndex = item
                    selectedRover = roverOptions[item]
                    selectedCamera = null
                    selectedCameraIndex = -1
                    binding.newRoverRover.text = selectedRover
                    dialog.dismiss()
                }
                .show()
        }
    }

    private fun setUpRoverCameraButton() {
        binding.newRoverCamera.setOnClickListener {
            if(selectedRover == null) {
                Toast.makeText(
                    requireContext(),
                    R.string.rover_no_rover_selected_response,
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }
            val singleChoiceItems = when(selectedRover) {
                "Curiosity" -> R.array.curiosity_rover_cameras
                "Opportunity", "Spirit" -> R.array.opportunity_spirit_rover_cameras
                else -> {
                    R.array.empty_array
                }
            }
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.camera_prompt)
                .setSingleChoiceItems(singleChoiceItems, selectedCameraIndex) { dialog, i ->
                    selectedCameraIndex = i
                    val cameraLong = if(selectedRover == "Curiosity") {
                        when(i) {
                            0 -> "Front Hazard Avoidance Camera"
                            1 -> "Rear Hazard Avoidance Camera"
                            2 -> "Mast Camera"
                            3 -> "Chemistry and Camera Complex"
                            4 -> "Mars Hand Lens Imager"
                            5 -> "Mars Descent Imager"
                            6 -> "Navigation Camera"
                            else -> ""
                        }
                    } else {
                        when(i) {
                            0 -> "Front Hazard Avoidance Camera"
                            1 -> "Rear Hazard Avoidance Camera"
                            2 -> "Navigation Camera"
                            3 -> "Panoramic Camera"
                            4 -> "Miniature Thermal Emission Spectrometer (Mini-TES)"
                            else -> ""
                        }
                    }

                    binding.newRoverCamera.text = cameraLong
                    selectedCamera = getCameraShortName(cameraLong)

                    dialog.dismiss()

                }.show()
        }
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
            if(
                selectedRover != null
                && selectedCamera != null
                && selectedDate != null
            ) {
                api.getRover(selectedDate!!, selectedRover!!, selectedCamera!!, callback = { rovers ->
                    roverListViewModel.addRovers(rovers)

                    // Return to initial fragment on success
                    val vp = requireActivity().findViewById(R.id.view_pager) as ViewPager2
                    vp.setCurrentItem(RoverListFragment.FRAGMENT_NUMBER, false)
                },
                error_callback = { volleyError ->
                    Log.d("RoverVolleyError", "Got error: ${volleyError.networkResponse?.statusCode}")
                    val response = when(volleyError.networkResponse.statusCode) {
                        400 -> "Invalid date! You can only request the current date or earlier."
                        else -> "There was an error retrieving your image. Try again."
                    }
                    Toast.makeText(
                        requireContext(),
                        response,
                        Toast.LENGTH_LONG
                    ).show()
                })
            } else {
                Log.d("submit button", "rover: $selectedRover, cam: $selectedCamera, date: $selectedDate")
            }
        }
    }

    private fun getCameraShortName(longName: String): String {
        return when(longName) {
            "Front Hazard Avoidance Camera" -> "FHAZ"
            "Rear Hazard Avoidance Camera" -> "RHAZ"
            "Mast Camera" -> "MAST"
            "Chemistry and Camera Complex" -> "CHEMCAM"
            "Mars Hand Lens Imager" -> "MAHLI"
            "Mars Descent Imager" -> "MARDI"
            "Navigation Camera" -> "NAVCAM"
            "Panoramic Camera" -> "PANCAM"
            "Miniature Thermal Emission Spectrometer (Mini-TES)" -> "MINITES"
            else -> ""
        }
    }

    companion object {
        const val FRAGMENT_NUMBER = 6
    }

}