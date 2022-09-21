package com.projectx.householdtasks.presentation.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

class EditProfileEmailViewModel: BaseViewModel() {
    val newEmail = MutableLiveData("")
    val isButtonEnabled = MediatorLiveData<Boolean>().apply {
        addSource(newEmail) {
            value = isContinueButtonEnabled()
        }
    }

    fun isEmailValid(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(newEmail.value!!.trim())
            .matches()
    }

    private fun isContinueButtonEnabled(): Boolean {
        return newEmail.value!!.isNotEmpty()
    }
}