package com.projectx.householdtasks.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.projectx.householdtasks.presentation.*
import kotlin.random.Random

class LoginViewModel : BaseViewModel() {

    private var _email = MutableLiveData("")
    var email: LiveData<String> = _email
    private var _password = MutableLiveData("")
    var password: LiveData<String> = _password
    private val _uiState: MutableLiveData<UiState> =
        MutableLiveData(UiState(LoginEmailResult.OK, LoginPasswordResult.OK, false, null))
    val uiState get() = _uiState

    val isButtonEnabled = MediatorLiveData<Boolean>().apply {
        addSource(email) {
            value = isContinueButtonEnabled()
        }
        addSource(password) {
            value = isContinueButtonEnabled()
        }
    }

    private fun isContinueButtonEnabled(): Boolean {
        return email.value!!.isNotEmpty() && password.value!!.isNotEmpty()
    }

    private fun isEmailValid(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email.value!!.trim()).matches()
    }

    private fun isPasswordValid(): Boolean {
        // TODO: add matching with password and Family Id
        return password.value!!.length >= MIN_PASSWORD_LENGTH
    }

    private fun validateEmail(): LoginEmailResult {
        if (!isEmailValid()) return LoginEmailResult.InvalidEmailError
        return LoginEmailResult.OK
    }

    private fun validatePassword(): LoginPasswordResult {
        if (!isPasswordValid()) return LoginPasswordResult.LengthError
        return LoginPasswordResult.OK
    }

    fun setEmailValue(email: String) {
        _email.value = email
    }

    fun setPasswordValue(password: String) {
        _password.value = password
    }

    fun isSaveButtonEnabled(): Boolean {
        return email.value!!.isNotEmpty() && password.value!!.isNotEmpty()
    }

    fun resetErrorForEmail() {
        _uiState.postValue(_uiState.value!!.copy(loginEmailResult = LoginEmailResult.OK))
    }

    fun resetErrorForPassword() {
        _uiState.postValue(_uiState.value!!.copy(loginPasswordResult = LoginPasswordResult.OK))
    }

    private fun isValid(): Boolean {
        if (validateEmail() == LoginEmailResult.OK && validatePassword() == LoginPasswordResult.OK) return true
        return false
    }

    fun handleSaveChanges() {

        if (!isValid()) {
            _uiState.postValue(
                UiState(
                    validateEmail(),
                    validatePassword(),
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
                    validateEmail(),
                    validatePassword(),
                    false,
                    RequestResult.Success
                )
            )
        } else {
            _uiState.postValue(
                UiState(
                    validateEmail(),
                    validatePassword(),
                    false,
                    RequestResult.RequestFailedError
                )
            )
        }
    }

    // TODO: send API request
    private fun sendRequest(): Boolean {
        return Random.nextBoolean()
    }

    data class UiState(
        val loginEmailResult: LoginEmailResult,
        val loginPasswordResult: LoginPasswordResult,

        val isLoading: Boolean,
        val requestResult: RequestResult?
    )

    companion object {
        const val MIN_PASSWORD_LENGTH = 8
        const val START_PARENT_LINK = 19
        const val END_PARENT_LINK = 39
        const val START_CHILD_LINK = 0
        const val END_CHILD_LINK = 21
    }
}