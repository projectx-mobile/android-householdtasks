package com.projectx.householdtasks.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class NotificationSharedViewModel : BaseViewModel() {

    private var _interval = MutableLiveData("Каждые 2 часа")
    val interval: LiveData<String> = _interval

    private var _isItemChecked = MutableLiveData(false)
    val isItemChecked: LiveData<Boolean> = _isItemChecked

    fun saveInterval(newInterval: String) {
        _interval.value = newInterval
    }

    fun setItemChecked(value: Boolean) {
        _isItemChecked.value = value
    }
}