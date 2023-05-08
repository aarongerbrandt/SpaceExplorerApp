package com.example.finalproject.apod.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.finalproject.NasaCaller
import com.example.finalproject.R
import com.example.finalproject.apod.ApodListAdapter
import com.example.finalproject.apod.ApodListViewModel
import com.example.finalproject.databinding.FragmentApodListBinding
import kotlinx.coroutines.launch

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

    private fun addButtonListener() {
        binding.addApodFab.setOnClickListener {
            val vp = requireActivity().findViewById(R.id.view_pager) as ViewPager2
            vp.setCurrentItem(NewApodFragment.FRAGMENT_NUMBER, false)
        }
    }

    companion object {
        const val FRAGMENT_NUMBER = 1
    }
}