package com.projectx.householdtasks.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.projectx.householdtasks.presentation.CurrentPasswordValidationResult
import com.projectx.householdtasks.presentation.NewPasswordValidationResult
import com.projectx.householdtasks.presentation.PasswordConfirmationValidationResult
import com.projectx.householdtasks.presentation.RequestResult
import com.projectx.householdtasks.presentation.event.EditProfilePasswordScreenEvent
import com.projectx.householdtasks.presentation.state.UiState
import com.projectx.householdtasks.presentation.state.UiState.Companion.process
import kotlin.random.Random

class EditProfilePasswordViewModel :
    BaseViewModel<EditProfilePasswordViewModel.EditProfilePasswordUiState, EditProfilePasswordScreenEvent>() {

    override val state = MutableLiveData<UiState<EditProfilePasswordUiState>>(
        UiState.Ready(EditProfilePasswordUiState())
    )

    override fun onEvent(event: EditProfilePasswordScreenEvent) {
        when (event) {
            is EditProfilePasswordScreenEvent.SetCurrentPasswordValue -> setCurrentPasswordValue(
                event.password
            )
            is EditProfilePasswordScreenEvent.SetNewPasswordValue -> setNewPasswordValue(event.password)
            is EditProfilePasswordScreenEvent.SetPasswordConfirmationValue -> setPasswordConfirmationValue(
                event.password
            )
            is EditProfilePasswordScreenEvent.ResetCurrentPasswordError -> resetCurrentPasswordError()
            is EditProfilePasswordScreenEvent.ResetNewPasswordError -> resetNewPasswordError()
            is EditProfilePasswordScreenEvent.ResetPasswordConfirmationError -> resetPasswordConfirmationError()
            else -> super.onEvent(event)
        }
    }

    private fun setCurrentPasswordValue(password: String) {
        process(state.value, onReady = {
            state.postValue(UiState.Ready(it.copy(currentPassword = password)))
        })
    }

    private fun setNewPasswordValue(password: String) {
        process(state.value, onReady = {
            state.postValue(UiState.Ready(it.copy(newPassword = password)))
        })
    }

    private fun setPasswordConfirmationValue(password: String) {
        process(state.value, onReady = {
            state.postValue(UiState.Ready(it.copy(passwordConfirmation = password)))
        })
    }

    private fun resetCurrentPasswordError() {
        process(state.value, onReady = {
            state.postValue(UiState.Ready(it.copy(currentPasswordValidationResult = CurrentPasswordValidationResult.OK)))
        })
    }

    private fun resetNewPasswordError() {
        process(state.value, onReady = {
            state.postValue(UiState.Ready(it.copy(newPasswordValidationResult = NewPasswordValidationResult.OK)))
        })
    }

    private fun resetPasswordConfirmationError() {
        process(state.value, onReady = {
            state.postValue(UiState.Ready(it.copy(confirmationValidationResult = PasswordConfirmationValidationResult.OK)))
        })
    }

    fun isSaveButtonEnabled(): Boolean {
        var result = false
        process(state.value, onReady = {
            result =
                it.currentPassword.isNotEmpty() && it.newPassword.isNotEmpty() && it.passwordConfirmation.isNotEmpty()
        })
        return result
    }

    private fun isPasswordValid(password: String): Boolean {
        // TODO: add matching with password
        return password.length >= MIN_PASSWORD_LENGTH
    }

    private fun isPasswordsMatch(): Boolean {
        var result = false
        process(state.value, onReady = {
            result = it.newPassword == it.passwordConfirmation
        })
        return result
    }

    private fun isPasswordsValid(): Boolean {
        if (validateCurrentPassword() == CurrentPasswordValidationResult.OK &&
            validateNewPassword() == NewPasswordValidationResult.OK &&
            validatePasswordConfirmation() == PasswordConfirmationValidationResult.OK
        ) return true
        return false
    }

    private fun isCurrentPasswordCoincide(): Boolean {
//        TODO: request
        return Random.nextBoolean()
    }

    private fun validateCurrentPassword(): CurrentPasswordValidationResult {
        var result: CurrentPasswordValidationResult = CurrentPasswordValidationResult.OK
        process(state.value, onReady = {
            result = when {
                !isPasswordValid(it.currentPassword) -> CurrentPasswordValidationResult.LengthError
                !isCurrentPasswordCoincide() -> CurrentPasswordValidationResult.InvalidPasswordError
                else -> result
            }
        })
        return result
    }

    private fun validateNewPassword(): NewPasswordValidationResult {
        var result: NewPasswordValidationResult = NewPasswordValidationResult.OK
        process(state.value, onReady = {
            result = when {
                !isPasswordValid(it.newPassword) -> NewPasswordValidationResult.LengthError
                else -> result
            }
        })
        return result
    }

    private fun validatePasswordConfirmation(): PasswordConfirmationValidationResult {
        var result: PasswordConfirmationValidationResult = PasswordConfirmationValidationResult.OK
        process(state.value, onReady = {
            result = when {
                !isPasswordValid(it.passwordConfirmation) -> PasswordConfirmationValidationResult.LengthError
                !isPasswordsMatch() -> PasswordConfirmationValidationResult.PasswordsMismatchError
                else -> result
            }
        })
        return result
    }

    fun handleSaveChanges() {
        process(state.value, onReady = {
            val requestSucceeded = sendRequest()
            val newState = EditProfilePasswordUiState(
                validateCurrentPassword(),
                validateNewPassword(),
                validatePasswordConfirmation(),
                when {
                    !isPasswordsValid() -> null
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

    data class EditProfilePasswordUiState(
        val currentPasswordValidationResult: CurrentPasswordValidationResult = CurrentPasswordValidationResult.OK,
        val newPasswordValidationResult: NewPasswordValidationResult = NewPasswordValidationResult.OK,
        val confirmationValidationResult: PasswordConfirmationValidationResult = PasswordConfirmationValidationResult.OK,
        val requestResult: RequestResult? = null,
        val currentPassword: String = "",
        val newPassword: String = "",
        val passwordConfirmation: String = ""
    )

    companion object {
        const val MIN_PASSWORD_LENGTH = 8
    }
}

