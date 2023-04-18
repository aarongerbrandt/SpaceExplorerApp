package com.example.finalproject.apod

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.ApodRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ApodListViewModel: ViewModel() {
    private val apodRepo = ApodRepository.get()

    private val dateFormat = SimpleDateFormat("yyyy-mm-dd", Locale.US)

    private val _apods: MutableStateFlow<List<Apod>> = MutableStateFlow(emptyList())
    val apods: StateFlow<List<Apod>>
        get() = _apods.asStateFlow()

    init {
        viewModelScope.launch {
            apodRepo.getApodResponses().collect {
                _apods.value = it.sortedWith(compareBy { apod ->
                    dateFormat.parse(apod.date)
                }).reversed()
            }
        }
    }

    fun addApod(apod:Apod) {
        viewModelScope.launch {
            if(apodRepo.countResponseByDate(apod.date) == 0) {
                apodRepo.addApodResponse(apod)
            }
        }
    }

}