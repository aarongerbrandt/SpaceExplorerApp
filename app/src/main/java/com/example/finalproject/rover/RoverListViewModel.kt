package com.example.finalproject.rover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.RoverRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RoverListViewModel: ViewModel() {
    private val roverRepo = RoverRepository.get()

    private val _rovers: MutableStateFlow<List<Rover>> = MutableStateFlow(emptyList())
    val rovers: StateFlow<List<Rover>>
        get() = _rovers.asStateFlow()

    init {
        viewModelScope.launch {
            roverRepo.getRoverResponses().collect {
                _rovers.value = it.sortedWith(compareBy { rover ->
                    rover.earth_date
                }).reversed()
            }
        }
    }

    fun addRover(rover:Rover) {
        viewModelScope.launch {
            if(roverRepo.countResponseByImageId(rover.image_id) <= 0) {
                roverRepo.addRoverResponse(rover)
            }
        }
    }

    fun addRovers(rovers: List<Rover>) {
        for(rover in rovers) {
            addRover(rover)
        }
    }
}