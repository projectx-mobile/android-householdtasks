package com.projectx.householdtasks.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.projectx.householdtasks.R
import com.projectx.householdtasks.presentation.EmailValidationResult
import com.projectx.householdtasks.presentation.RequestResult
import com.projectx.householdtasks.presentation.event.EditProfileEmailScreenEvent
import com.projectx.householdtasks.presentation.state.UiState
import com.projectx.householdtasks.presentation.state.UiState.Companion.process
import kotlin.random.Random

class EditProfileEmailViewModel :
    BaseViewModel<EditProfileEmailViewModel.EditProfileEmailUiState, EditProfileEmailScreenEvent>() {

    override val state = MutableLiveData<UiState<EditProfileEmailUiState>>(
        UiState.Ready(EditProfileEmailUiState(EmailValidationResult.OK))
    )

    override fun onEvent(event: EditProfileEmailScreenEvent) {
        when (event) {
            is EditProfileEmailScreenEvent.NavigateToProfile -> event.navController.navigate(R.id.profileFragment)
            is EditProfileEmailScreenEvent.HandleSaveChanges -> handleSaveChanges()
            else -> super.onEvent(event)
        }
    }

    fun setNewEmailValue(newEmail: String) {
        process(state.value, onReady = {
            state.postValue(
                UiState.Ready(it.copy(newEmail = newEmail))
            )
        })
    }

    fun isSaveButtonEnabled(): Boolean {
        var result = false
        process(state.value, onReady = {
            result = it.newEmail.isNotEmpty()
        })
        return result
    }

    fun resetEmailError() {
        //state.postValue(state.value.copy(emailValidationResult = EmailValidationResult.OK))
    }

    fun handleSaveChanges() {
        val emailValidationResult = validateEmail()
        process(state.value, onReady = {
            val newState = it.copy(
                emailValidationResult = emailValidationResult, requestResult = when {
                    emailValidationResult != EmailValidationResult.OK -> null
                    else -> {
                        val requestSucceeded = sendRequest()
                        if (requestSucceeded) {
                            RequestResult.Success
                        } else {
                            RequestResult.RequestFailedError
                        }
                    }
                }
            )
            state.postValue(UiState.Ready(newState))
        })
    }

    private fun isEmailValid(): Boolean {
        var result = false
        process(state.value, onReady = {
            result = android.util.Patterns.EMAIL_ADDRESS.matcher(it.newEmail.trim()).matches()
        })
        return result
    }

    private fun validateEmail(): EmailValidationResult {
        return when (isEmailValid()) {
            true -> EmailValidationResult.OK
            false -> EmailValidationResult.InvalidEmailError
        }
    }

    //  TODO: request
    private fun sendRequest(): Boolean {
        return Random.nextBoolean()
    }

    data class EditProfileEmailUiState(
        val emailValidationResult: EmailValidationResult,
        val newEmail: String = "",
        val requestResult: RequestResult? = null
    )
}
