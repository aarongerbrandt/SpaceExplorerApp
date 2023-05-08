package com.example.finalproject.rover.fragments

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
import com.example.finalproject.databinding.FragmentRoverListBinding
import com.example.finalproject.rover.RoverListAdapter
import com.example.finalproject.rover.RoverListViewModel
import kotlinx.coroutines.launch


private const val TAG = "RoverListFragment"
class RoverListFragment : Fragment() {
    private lateinit var api:NasaCaller
    private val roverListViewModel: RoverListViewModel by viewModels()

    private var _binding: FragmentRoverListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            roverListViewModel.rovers.collect { rovers ->
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

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addButtonListener() {
        binding.addRoverFab.setOnClickListener {
            val vp = requireActivity().findViewById(R.id.view_pager) as ViewPager2
            vp.setCurrentItem(NewRoverFragment.FRAGMENT_NUMBER, false)
        }
    }

    companion object {
        const val FRAGMENT_NUMBER = 2
    }
}