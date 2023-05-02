package com.example.finalproject.apod.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.NasaCaller
import com.example.finalproject.apod.ApodListAdapter
import com.example.finalproject.apod.ApodListViewModel
import com.example.finalproject.databinding.FragmentApodListBinding
import kotlinx.coroutines.launch
import java.util.*

private const val TAG = "ApodListFragment"
class ApodListFragment: Fragment() {
    private lateinit var api:NasaCaller

    private val apodListViewModel: ApodListViewModel by viewModels()

    private var _binding: FragmentApodListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            apodListViewModel.apods.collect { apods ->
                binding.apodRecyclerView.adapter = ApodListAdapter(apods)
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
        _binding = FragmentApodListBinding.inflate(inflater, container, false)
        binding.apodRecyclerView.layoutManager = LinearLayoutManager(context)

        addButtonListener()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getImageFromDate(date:String) {
        api.getApod(date, callback = { apodResponse ->
            apodListViewModel.addApod(apodResponse)
            viewLifecycleOwner.lifecycleScope.launch {
                apodListViewModel.apods.collect { apods ->
                    binding.apodRecyclerView.adapter = ApodListAdapter(apods)
                }
            }
        })
    }

    private fun addButtonListener() {
        binding.apodOpenDialogButton.setOnClickListener {
            val c = Calendar.getInstance()

            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, monthOfYear, dayOfMonth ->
                    getImageFromDate("${"%04d".format(year)}-${"%02d".format(monthOfYear+1)}-${"%02d".format(dayOfMonth)}")
                },
                year,
                month,
                day
            )

            datePickerDialog.show()
        }
    }
}