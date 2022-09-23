package com.projectx.householdtasks.presentation.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

class InviteUserByEmailViewModel : BaseViewModel() {
    val email = MutableLiveData("")
    val isButtonEnabled = MediatorLiveData<Boolean>().apply {
        addSource(email) {
            value = isContinueButtonEnabled()
        }
    }

    fun isEmailValid(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email.value!!.trim()).matches()
    }

    private fun isContinueButtonEnabled(): Boolean {
        return email.value!!.isNotEmpty()
    }
}
