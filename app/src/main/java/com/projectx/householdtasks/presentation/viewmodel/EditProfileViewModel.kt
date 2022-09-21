package com.projectx.householdtasks.presentation.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

class EditProfileViewModel: BaseViewModel() {
    val newName = MutableLiveData("")
    val isButtonEnabled = MediatorLiveData<Boolean>().apply {
        addSource(newName) {
            value = isSaveButtonEnabled()
        }
    }

    fun isNameValid(): Boolean {
        return newName.value!!.length in 1..20   }

    private fun isSaveButtonEnabled(): Boolean {
        return newName.value!!.isNotEmpty()
    }
}