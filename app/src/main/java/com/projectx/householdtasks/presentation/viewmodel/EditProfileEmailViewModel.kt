package com.projectx.householdtasks.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.projectx.householdtasks.presentation.EmailValidationResult
import com.projectx.householdtasks.presentation.RequestResult
import kotlin.random.Random

class EditProfileEmailViewModel : BaseViewModel() {
    private val _newEmail = MutableLiveData("")
    val newEmail: LiveData<String> = Transformations.distinctUntilChanged(_newEmail)
    private val _uiState: MutableLiveData<UiState> = MutableLiveData(
        UiState(EmailValidationResult.OK, false, null)
    )
    val uiState get() = _uiState

    fun setNewEmailValue(newEmail: String) {
        _newEmail.value = newEmail
    }

    fun isSaveButtonEnabled(): Boolean {
        return newEmail.value!!.isNotEmpty()
    }

    fun resetEmailError() {
        _uiState.postValue(_uiState.value!!.copy(emailValidationResult = EmailValidationResult.OK))
    }

    fun handleSaveChanges() {
        val emailValidationResult = validateEmail()

        if (emailValidationResult != EmailValidationResult.OK) {
            _uiState.postValue(UiState(emailValidationResult, false, null))
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

    private fun isEmailValid(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(newEmail.value!!.trim())
            .matches()
    }

    private fun validateEmail(): EmailValidationResult {
        if (!isEmailValid()) return EmailValidationResult.InvalidEmailError
        return EmailValidationResult.OK
    }

    //  TODO: request
    private fun sendRequest(): Boolean {
        return Random.nextBoolean()
    }

    data class UiState(
        val emailValidationResult: EmailValidationResult,

        val isLoading: Boolean,
        val requestResult: RequestResult?
    )
}
