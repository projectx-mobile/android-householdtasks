package com.projectx.householdtasks.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.projectx.householdtasks.presentation.EmailValidationResult
import com.projectx.householdtasks.presentation.RequestResult
import kotlin.random.Random

class InviteUserByEmailViewModel : BaseViewModel() {
    private val _email = MutableLiveData("")
    val email: LiveData<String> = Transformations.distinctUntilChanged(_email)
    private val _uiState: MutableLiveData<UiState> = MutableLiveData(
        UiState(EmailValidationResult.OK, false, null)
    )
    val uiState get() = _uiState

    fun setEmailValue(email: String) {
        _email.value = email
    }

    private fun isEmailValid(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email.value!!.trim()).matches()
    }

    fun isSaveButtonEnabled(): Boolean {
        return email.value!!.isNotEmpty()
    }

    fun resetEmailError() {
        _uiState.postValue(_uiState.value!!.copy(emailValidationResult = EmailValidationResult.OK))
    }

    fun handleSaveChanges() {
        val emailValidationResult = validateEmail()

        if (emailValidationResult != EmailValidationResult.OK) {
            _uiState.postValue(
                UiState(
                    emailValidationResult,
                    false,
                    null
                )
            )
            return
        }

        val requestSucceeded = sendRequest()
        if (requestSucceeded) {
            _uiState.postValue(
                UiState(
                    emailValidationResult,
                    false,
                    RequestResult.Success
                )
            )
        } else {
            _uiState.postValue(
                UiState(
                    emailValidationResult,
                    false,
                    RequestResult.RequestFailedError
                )
            )
        }
    }

    //  TODO: request
    private fun sendRequest(): Boolean {
        return Random.nextBoolean()
    }

    private fun validateEmail(): EmailValidationResult {
        if (!isEmailValid()) return EmailValidationResult.InvalidEmailError
        return EmailValidationResult.OK
    }

    data class UiState(
        val emailValidationResult: EmailValidationResult,

        val isLoading: Boolean,
        val requestResult: RequestResult?
    )
}
