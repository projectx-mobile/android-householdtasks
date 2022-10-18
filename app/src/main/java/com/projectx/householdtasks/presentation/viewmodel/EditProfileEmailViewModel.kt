package com.projectx.householdtasks.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.projectx.householdtasks.R
import com.projectx.householdtasks.presentation.EmailValidationResult
import com.projectx.householdtasks.presentation.RequestResult
import com.projectx.householdtasks.presentation.event.EditProfileEmailScreenEvent
import com.projectx.householdtasks.presentation.state.UiState
import kotlin.random.Random

class EditProfileEmailViewModel :
    BaseViewModel<EditProfileEmailViewModel.EditProfileEmailUiState, EditProfileEmailScreenEvent>() {

    override val state = MutableLiveData<UiState<EditProfileEmailUiState>>(
        UiState.Ready(EditProfileEmailUiState(MutableLiveData(EmailValidationResult.OK)))
    )

    override fun onEvent(event: EditProfileEmailScreenEvent) {
        when (event) {
            is EditProfileEmailScreenEvent.NavigateToProfile -> event.navController.navigate(R.id.profileFragment)
            is EditProfileEmailScreenEvent.HandleSaveChanges -> handleSaveChanges()
            else -> super.onEvent(event)
        }
    }

    fun setNewEmailValue(newEmail: String) {
        when (state.value) {
            is UiState.Ready -> (state.value as UiState.Ready<EditProfileEmailUiState>).data.newEmail.value =
                newEmail
            else -> {}
        }
    }

    fun isSaveButtonEnabled(): Boolean {
        return when (state.value) {
            is UiState.Ready -> (state.value as UiState.Ready<EditProfileEmailUiState>).data.newEmail.value?.isNotEmpty()
                ?: false
            else -> false
        }
    }

    fun resetEmailError() {
        //state.postValue(state.value.copy(emailValidationResult = EmailValidationResult.OK))
    }

    fun handleSaveChanges() {
        val emailValidationResult = validateEmail()
        when (state.value) {
            is UiState.Ready -> {
                (state.value as UiState.Ready<EditProfileEmailUiState>).apply {
                    data.emailValidationResult.value = emailValidationResult
                    data.requestResult.value = when {
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
                }
            }
            else -> {}
        }
    }

    private fun isEmailValid(): Boolean {
        return when (state.value) {
            is UiState.Ready -> android.util.Patterns.EMAIL_ADDRESS.matcher(
                (state.value as UiState.Ready<EditProfileEmailUiState>).data.newEmail.value?.trim()
                    ?: ""
            ).matches()
            else -> false
        }
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
        val emailValidationResult: MutableLiveData<EmailValidationResult>,
        val newEmail: MutableLiveData<String> = MutableLiveData<String>(""),
        val requestResult: MutableLiveData<RequestResult?> = MutableLiveData(null)
    )
}
