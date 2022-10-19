package com.projectx.householdtasks.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.projectx.householdtasks.R
import com.projectx.householdtasks.presentation.EmailValidationResult
import com.projectx.householdtasks.presentation.RequestResult
import com.projectx.householdtasks.presentation.event.InviteUserByEmailScreenEvent
import com.projectx.householdtasks.presentation.state.UiState
import kotlin.random.Random

class InviteUserByEmailViewModel :
    BaseViewModel<InviteUserByEmailViewModel.InviteUserByEmailUiState, InviteUserByEmailScreenEvent>() {

    override val state = MutableLiveData<UiState<InviteUserByEmailUiState>>(
        UiState.Ready(InviteUserByEmailUiState())
    )

    override fun onEvent(event: InviteUserByEmailScreenEvent) {
        when (event) {
            is InviteUserByEmailScreenEvent.SetEmailValue -> setEmailValue(event.email)
            is InviteUserByEmailScreenEvent.ResetEmailError -> resetEmailError()
            is InviteUserByEmailScreenEvent.HandleSaveChanges -> handleSaveChanges()
            is InviteUserByEmailScreenEvent.NavigateToProfile -> event.navController.navigate(R.id.profileFragment)
            else -> super.onEvent(event)
        }
    }

    private fun setEmailValue(email: String) {
        UiState.process(state.value, onReady = {
            state.postValue(UiState.Ready(it.copy(email = email)))
        })
    }

    private fun isEmailValid(): Boolean {
        var result = false
        UiState.process(state.value, onReady = {
            result = android.util.Patterns.EMAIL_ADDRESS.matcher(it.email.trim()).matches()
        })
        return result
    }

    fun isSaveButtonEnabled(): Boolean {
        var result = false
        UiState.process(state.value, onReady = {
            result = it.email.isNotEmpty()
        })
        return result
    }

    private fun resetEmailError() {
        UiState.process(state.value, onReady = {
            state.postValue(UiState.Ready(it.copy(emailValidationResult = EmailValidationResult.OK)))
        })
    }

    fun handleSaveChanges() {
        val emailValidationResult = validateEmail()
        UiState.process(state.value, onReady = {
            val requestSucceeded = sendRequest()
            val newState = it.copy(
                emailValidationResult = emailValidationResult,
                requestResult = when {
                    emailValidationResult != EmailValidationResult.OK -> null
                    requestSucceeded -> RequestResult.Success
                    else -> RequestResult.RequestFailedError
                }
            )
            state.postValue(UiState.Ready(newState))
        })
    }

    //TODO: request
    private fun sendRequest(): Boolean {
        return Random.nextBoolean()
    }

    private fun validateEmail(): EmailValidationResult {
        if (!isEmailValid()) return EmailValidationResult.InvalidEmailError
        return EmailValidationResult.OK
    }

    data class InviteUserByEmailUiState(
        val email: String = "",
        val emailValidationResult: EmailValidationResult = EmailValidationResult.OK,
        val requestResult: RequestResult? = null
    )
}
