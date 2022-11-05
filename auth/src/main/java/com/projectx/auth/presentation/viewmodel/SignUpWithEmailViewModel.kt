package com.projectx.auth.presentation.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.projectx.common.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignUpWithEmailViewModel: BaseViewModel() {

    private val _emailFlow = MutableStateFlow("")
    val emailFlow = _emailFlow.asStateFlow()

    private val _buttonState = MutableStateFlow(false)
    val buttonState = _buttonState.asStateFlow()

    val email = MutableLiveData("")
    val isButtonEnabled = MediatorLiveData<Boolean>().apply {
        addSource(email) {
            value = isContinueButtonEnabled()
        }
    }

    private fun isEmailValid(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email.value!!).matches()
    }

    fun isValid(): Boolean {
        return isEmailValid()
    }

    private fun isContinueButtonEnabled(): Boolean {
        return email.value!!.isNotEmpty()
    }

    fun checkEmail() {
//        navigate(NavEvent.To(SignUpWithEmailFragmentDirections.actionSignUpWithEmailFragmentToSignUpEmailConfirmationFragment()))
    }
}