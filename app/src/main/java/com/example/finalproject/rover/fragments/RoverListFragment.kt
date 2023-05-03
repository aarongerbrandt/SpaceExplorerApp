package com.example.finalproject.rover.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.NasaCaller
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentRoverListBinding
import com.example.finalproject.rover.RoverListAdapter
import com.example.finalproject.rover.RoverListViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


private const val TAG = "RoverListFragment"
class RoverListFragment : Fragment() {
    private lateinit var api:NasaCaller
    private val roverListViewModel: RoverListViewModel by viewModels()

    private var _binding: FragmentRoverListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val calendarDateFormatter = SimpleDateFormat("yyyy-MM-d", Locale.US)

    private var selectedRover:String? = null
    private var selectedCamera:String? = null
        set(value) { field = value?.let { getCameraShortName(it) } }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            roverListViewModel.rovers.collect { rovers ->
//                Log.d(TAG, "Collecting rovers: $rovers")
                binding.roverRecyclerView.adapter = RoverListAdapter(rovers)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        api = NasaCaller(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoverListBinding.inflate(inflater, container, false)
        binding.roverRecyclerView.layoutManager = LinearLayoutManager(context)

        addButtonListener()
        loadSpinners()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getImageFromDate(date:Date) {
        if(date == null || selectedCamera == null || selectedRover == null) {
            Toast.makeText(
                requireContext(),
                "You must select a rover, camera and date first.",
                Toast.LENGTH_LONG
            ).show()
        } else {
            api.getRover(date, selectedRover!!, selectedCamera!!, callback = { rovers ->
                Log.d(TAG, rovers.toString())
                roverListViewModel.addRovers(rovers)
                viewLifecycleOwner.lifecycleScope.launch {
                    roverListViewModel.rovers.collect { rovers ->
                        binding.roverRecyclerView.adapter = RoverListAdapter(rovers)
                    }
                }
            })
        }
    }

    private fun addButtonListener() {
        binding.roverOpenDialogButton.setOnClickListener {
            val c = Calendar.getInstance()

            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)



            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, selectedYear, monthOfYear, dayOfMonth ->
                    val date = calendarDateFormatter.parse("$selectedYear-$monthOfYear-$dayOfMonth")
                    getImageFromDate(date!!)
                },
                year,
                month,
                day
            )

            datePickerDialog.show()
        }
    }

    private fun loadSpinners() {
        val roverSpinner = binding.roverSpinner
        val cameraSpinner = binding.roverCameraSpinner

        var firstTime = true

        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.rover_options,
            R.layout.spinner_text
        ).also { arrayAdapter ->
            arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown)
            roverSpinner.adapter = arrayAdapter
        }

        roverSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Handle item select event
                if(firstTime) {
                    firstTime = false
                } else {
                    val selectedItem = parent.getItemAtPosition(position).toString()
                    if(selectedItem == "") {
                        return
                    }

                    selectedRover = selectedItem
                    loadSpinner(cameraSpinner, selectedItem)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun loadSpinner(spinner: Spinner, selectedRover: String) {
        val options = when(selectedRover) {
            "Curiosity" -> R.array.curiosity_rover_cameras
            "Opportunity", "Spirit" -> R.array.opportunity_spirit_rover_cameras
            else -> return
        }
        // Used to prevent select event taking place on fragment load
        var firstTime = true

        ArrayAdapter.createFromResource(
            this.requireContext(),
            options,
            R.layout.spinner_text
        ).also { arrayAdapter ->
            arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown)
            spinner.adapter = arrayAdapter
            spinner.visibility = View.VISIBLE
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(firstTime) {
                    firstTime = false
                } else {
                    val selectedItem = parent?.getItemAtPosition(position).toString()
                    selectedCamera = selectedItem
                    binding.roverOpenDialogButton.visibility = View.VISIBLE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun getCameraShortName(longName: String): String? {
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
            else -> null
        }
    }
}