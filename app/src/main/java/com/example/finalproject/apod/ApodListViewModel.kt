package com.example.finalproject.apod

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.ApodRepository
import com.example.finalproject.util.DateFormats
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ApodListViewModel: ViewModel() {
    private val apodRepo = ApodRepository.get()

    private val _apods: MutableStateFlow<List<Apod>> = MutableStateFlow(emptyList())
    val apods: StateFlow<List<Apod>>
        get() = _apods.asStateFlow()

    init {
        viewModelScope.launch {
            apodRepo.getApodResponses().collect {
                _apods.value = it.sortedWith(compareBy { apod ->
                    DateFormats.NASA_FORMAT.parse(apod.date)
                }).reversed()
            }
        }
    }

    fun addApod(apod: Apod) {
        viewModelScope.launch {
            val count = apodRepo.countResponseByDate(apod.date)
            if(count == 0) {
                apodRepo.addApodResponse(apod)
            } else {
                Log.d("ApodListViewModel", "Count: $count")
            }
        }
    }

}