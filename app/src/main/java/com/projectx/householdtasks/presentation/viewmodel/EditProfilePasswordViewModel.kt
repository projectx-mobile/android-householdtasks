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

    fun isPasswordValid(): Boolean {
        return currentPassword.value?.length!! > 7
    }

    fun isValid(): Boolean {
        return isPasswordValid()
    }

    private fun isContinueButtonEnabled(): Boolean {
        return currentPassword.value!!.isNotEmpty() && newPassword.value!!.isNotEmpty() && passwordConfirmation.value!!.isNotEmpty()
    }


}