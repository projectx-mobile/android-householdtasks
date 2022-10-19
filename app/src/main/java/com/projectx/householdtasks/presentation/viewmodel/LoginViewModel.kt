package com.projectx.householdtasks.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.projectx.householdtasks.R
import com.projectx.householdtasks.presentation.LoginEmailResult
import com.projectx.householdtasks.presentation.LoginPasswordResult
import com.projectx.householdtasks.presentation.RequestResult
import com.projectx.householdtasks.presentation.event.LoginScreenEvent
import com.projectx.householdtasks.presentation.state.UiState
import kotlin.random.Random

class LoginViewModel : BaseViewModel<LoginViewModel.LoginScreenUiState, LoginScreenEvent>() {
//TODO: REFACTOR LOGIC
    override val state = MutableLiveData<UiState<LoginScreenUiState>>(
        UiState.Ready(LoginScreenUiState())
    )

    override fun onEvent(event: LoginScreenEvent) {
        when (event) {
            is LoginScreenEvent.SetEmailValue -> setEmailValue(event.email)
            is LoginScreenEvent.SetPasswordValue -> setPasswordValue(event.password)
            is LoginScreenEvent.ResetEmailError -> resetErrorForEmail()
            is LoginScreenEvent.ResetPasswordError -> resetErrorForPassword()
            is LoginScreenEvent.NavigateToProfile -> event.navController.navigate(R.id.profileFragment)
            else -> super.onEvent(event)
        }
    }

    private fun isEmailValid(): Boolean {
        var result = false
        UiState.process(state.value, onReady = {
            result = android.util.Patterns.EMAIL_ADDRESS.matcher(it.email.trim()).matches()
        })
        return result
    }

    private fun isPasswordValid(): Boolean {
        var result = false
        UiState.process(state.value, onReady = {
            //TODO: add matching with password and Family Id
            result = it.password.length >= MIN_PASSWORD_LENGTH
        })
        return result
    }

    private fun validateEmail(): LoginEmailResult {
        if (!isEmailValid()) return LoginEmailResult.InvalidEmailError
        return LoginEmailResult.OK
    }

    private fun validatePassword(): LoginPasswordResult {
        if (!isPasswordValid()) return LoginPasswordResult.LengthError
        return LoginPasswordResult.OK
    }

    private fun setEmailValue(email: String) {
        UiState.process(state.value, onReady = {
            state.postValue(UiState.Ready(it.copy(email = email)))
        })
    }

    private fun setPasswordValue(password: String) {
        UiState.process(state.value, onReady = {
            state.postValue(UiState.Ready(it.copy(password = password)))
        })
    }

    fun isSaveButtonEnabled(): Boolean {
        var result = false
        UiState.process(state.value, onReady = {
            result = it.email.isNotEmpty() && it.password.isNotEmpty()
        })
        return result
    }

    private fun resetErrorForEmail() {
        UiState.process(state.value, onReady = {
            state.postValue(UiState.Ready(it.copy(loginEmailResult = LoginEmailResult.OK)))
        })
    }

    private fun resetErrorForPassword() {
        UiState.process(state.value, onReady = {
            state.postValue(UiState.Ready(it.copy(loginPasswordResult = LoginPasswordResult.OK)))
        })
    }

    private fun isValid(): Boolean {
        if (validateEmail() == LoginEmailResult.OK && validatePassword() == LoginPasswordResult.OK) return true
        return false
    }

    fun handleSaveChanges() {
        UiState.process(state.value, onReady = {
            val requestSucceeded = sendRequest()
            val newState = it.copy(
                loginEmailResult = validateEmail(),
                loginPasswordResult = validatePassword(),
                requestResult = when {
                    !isValid() -> null
                    requestSucceeded -> RequestResult.Success
                    else -> RequestResult.RequestFailedError
                }
            )
            state.postValue(UiState.Ready(newState))
        })
    }

    // TODO: send API request
    private fun sendRequest(): Boolean {
        return Random.nextBoolean()
    }

    data class LoginScreenUiState(
        val email: String = "",
        val password: String = "",
        val loginEmailResult: LoginEmailResult = LoginEmailResult.OK,
        val loginPasswordResult: LoginPasswordResult = LoginPasswordResult.OK,
        val requestResult: RequestResult? = null
    )

    companion object {
        const val MIN_PASSWORD_LENGTH = 8
        const val START_PARENT_LINK = 19
        const val END_PARENT_LINK = 39
        const val START_CHILD_LINK = 0
        const val END_CHILD_LINK = 21
    }
}