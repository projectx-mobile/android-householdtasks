package com.projectx.householdtasks.presentation.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

class LoginViewModel : BaseViewModel() {
    val email = MutableLiveData("")
    val password = MutableLiveData("")
    val isButtonEnabled = MediatorLiveData<Boolean>().apply {
        addSource(email) {
            value = isContinueButtonEnabled()
        }
        addSource(password) {
            value = isContinueButtonEnabled()
        }
    }

    fun isEmailValid(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email.value!!.trim()).matches()
    }

    fun isPasswordValid(): Boolean {
        // TODO: add matching with password and Family Id
        return password.value!!.length > MIN_PASSWORD_LENGTH
    }

    fun isValid(): Boolean {
        return isEmailValid() && isPasswordValid()
    }

    private fun isContinueButtonEnabled(): Boolean {
        return email.value!!.isNotEmpty() && password.value!!.isNotEmpty()
    }

    fun createRequest() {}
    companion object {
        const val MIN_PASSWORD_LENGTH = 7
        const val START_PARENT_LINK = 19
        const val END_PARENT_LINK = 39
        const val START_CHILD_LINK = 0
        const val END_CHILD_LINK = 21

    }
}