package com.projectx.auth.presentation.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.projectx.common.presentation.viewmodel.BaseViewModel

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
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email.value!!).matches()
    }

    fun isPasswordValid(): Boolean {
        // TODO: add matching with password and Family Id
        return password.value!!.length > 7
    }

    fun isValid(): Boolean {
        return isEmailValid() && isPasswordValid()
    }

    private fun isContinueButtonEnabled(): Boolean {
        return email.value!!.isNotEmpty() && password.value!!.isNotEmpty()
    }

    companion object{

    }
}