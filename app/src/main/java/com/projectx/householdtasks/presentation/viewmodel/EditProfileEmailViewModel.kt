package com.projectx.householdtasks.presentation.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

class EditProfileEmailViewModel: BaseViewModel() {
    val email = MutableLiveData("")
    val newEmail = MutableLiveData("")
    val isButtonEnabled = MediatorLiveData<Boolean>().apply {
        addSource(email) {
            value = isContinueButtonEnabled()
        }
        addSource(newEmail) {
            value = isContinueButtonEnabled()
        }
    }

    fun isEmailValid(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email.value!!).matches()
    }

    fun isValid(): Boolean {
        return isEmailValid()
    }

    private fun isContinueButtonEnabled(): Boolean {
        return email.value!!.isNotEmpty() && newEmail.value!!.isNotEmpty()
    }
}