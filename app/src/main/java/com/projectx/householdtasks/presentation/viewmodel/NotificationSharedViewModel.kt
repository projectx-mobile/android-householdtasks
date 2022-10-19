package com.projectx.householdtasks.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.projectx.householdtasks.presentation.FamilyMember
import com.projectx.householdtasks.presentation.adapter.NotificationSwitchersModel

class NotificationSharedViewModel : ViewModel() {

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

    data class NotificationScreenUiState(
        val switchersList: List<NotificationSwitchersModel>,
        val familyList: List<FamilyMember>
    )
}