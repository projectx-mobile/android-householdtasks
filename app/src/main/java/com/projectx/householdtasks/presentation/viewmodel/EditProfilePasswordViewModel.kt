package com.projectx.householdtasks.presentation.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.textfield.TextInputLayout

class EditProfilePasswordViewModel: BaseViewModel() {
    val currentPassword = MutableLiveData("")
    val newPassword = MutableLiveData("")
    val passwordConfirmation = MutableLiveData("")
    val isButtonEnabled = MediatorLiveData<Boolean>().apply {
        addSource(currentPassword) {
            value = isContinueButtonEnabled()
        }
        addSource(newPassword) {
            value = isContinueButtonEnabled()
        }
        addSource(passwordConfirmation) {
            value = isContinueButtonEnabled()
        }
    }

    fun isCurrentPasswordValid(): Boolean {
        // TODO: add matching with password
        return currentPassword.value!!.length > 7
    }

    fun isNewPasswordValid(): Boolean {
        return newPassword.value!!.length > 7
    }
    fun isPasswordConfirmationValid(): Boolean {
        return passwordConfirmation.value!!.length > 7
    }
    fun isPasswordsMatch(): Boolean {
        return newPassword.value == passwordConfirmation.value
    }

    fun isValid(): Boolean {
        return isCurrentPasswordValid() && isNewPasswordValid() && isPasswordConfirmationValid() && isPasswordsMatch()
    }

    private fun isContinueButtonEnabled(): Boolean {
        return currentPassword.value!!.isNotEmpty() && newPassword.value!!.isNotEmpty() && passwordConfirmation.value!!.isNotEmpty()
    }
}