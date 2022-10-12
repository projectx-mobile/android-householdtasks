package com.projectx.householdtasks.presentation.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged

class OnBoardingViewModel : BaseViewModel() {

    private val _imagePreviousPosition = MutableLiveData<Int>()
    private val _imagePosition = MutableLiveData<Int>()

    /**
     * Pair: prev position, cur position
     */
    val positionChange = MediatorLiveData<Pair<Int?, Int?>>().apply {
        addSource(_imagePreviousPosition) {
            value = it to value?.second
        }
        addSource(_imagePosition) {
            value = value?.first to it
        }
    }.distinctUntilChanged()

    fun onImageSelected(position: Int) {
        _imagePosition.value = position
    }

    fun onImageUnselected(position: Int) {
        _imagePreviousPosition.value = position
    }
}