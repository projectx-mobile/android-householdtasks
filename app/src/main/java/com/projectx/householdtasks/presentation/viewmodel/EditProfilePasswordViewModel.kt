package com.projectx.householdtasks.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.projectx.householdtasks.presentation.*
import kotlin.random.Random

class EditProfilePasswordViewModel : BaseViewModel() {
    private val _currentPassword = MutableLiveData("")
    val currentPassword: LiveData<String> = Transformations.distinctUntilChanged(_currentPassword)
    private val _newPassword = MutableLiveData("")
    val newPassword: LiveData<String> = Transformations.distinctUntilChanged(_newPassword)
    private val _passwordConfirmation = MutableLiveData("")
    val passwordConfirmation: LiveData<String> =
        Transformations.distinctUntilChanged(_passwordConfirmation)

    private val _uiState: MutableLiveData<UiState> =
        MutableLiveData(
            UiState(
                CurrentPasswordValidationResult.OK,
                NewPasswordValidationResult.OK,
                PasswordConfirmationValidationResult.OK,
                false,
                null
            )
        )
    val uiState get() = _uiState

    fun setCurrentPasswordValue(password: String) {
        _currentPassword.value = password
    }

    fun setNewPasswordValue(password: String) {
        _newPassword.value = password
    }

    fun setPasswordConfirmationValue(password: String) {
        _passwordConfirmation.value = password
    }

    fun resetCurrentPasswordError() {
        _uiState.postValue(
            _uiState.value!!.copy(
                currentPasswordValidationResult = CurrentPasswordValidationResult.OK,
            )
        )
    }

    fun resetNewPasswordError() {
        _uiState.postValue(
            _uiState.value!!.copy(
                newPasswordValidationResult = NewPasswordValidationResult.OK,
            )
        )
    }

    fun resetPasswordConfirmationError() {
        _uiState.postValue(
            _uiState.value!!.copy(
                confirmationValidationResult = PasswordConfirmationValidationResult.OK,
            )
        )
    }

    fun isSaveButtonEnabled(): Boolean {
        return currentPassword.value!!.isNotEmpty() && newPassword.value!!.isNotEmpty() && passwordConfirmation.value!!.isNotEmpty()
    }

    private fun isPasswordValid(password: String): Boolean {
        // TODO: add matching with password
        return password.length >= MIN_PASSWORD_LENGTH
    }

    private fun isPasswordsMatch(): Boolean {
        return newPassword.value == passwordConfirmation.value
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
        if (!isPasswordValid(currentPassword.value!!)) return CurrentPasswordValidationResult.LengthError
        if (!isCurrentPasswordCoincide()) return CurrentPasswordValidationResult.InvalidPasswordError
        return CurrentPasswordValidationResult.OK
    }

    private fun validateNewPassword(): NewPasswordValidationResult {
        if (!isPasswordValid(newPassword.value!!)) return NewPasswordValidationResult.LengthError
        return NewPasswordValidationResult.OK
    }

    private fun validatePasswordConfirmation(): PasswordConfirmationValidationResult {
        if (!isPasswordValid(passwordConfirmation.value!!)) return PasswordConfirmationValidationResult.LengthError
        if (!isPasswordsMatch()) return PasswordConfirmationValidationResult.PasswordsMismatchError
        return PasswordConfirmationValidationResult.OK
    }

    fun handleSaveChanges() {
        if (!isPasswordsValid()) {
            _uiState.postValue(
                UiState(
                    validateCurrentPassword(),
                    validateNewPassword(),
                    validatePasswordConfirmation(),
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
                    validateCurrentPassword(),
                    validateNewPassword(),
                    validatePasswordConfirmation(),
                    false,
                    RequestResult.Success
                )
            )
        } else {
            _uiState.postValue(
                UiState(
                    validateCurrentPassword(),
                    validateNewPassword(),
                    validatePasswordConfirmation(),
                    false,
                    RequestResult.RequestFailedError
                )
            )
        }
    }

    data class UiState(
        val currentPasswordValidationResult: CurrentPasswordValidationResult,
        val newPasswordValidationResult: NewPasswordValidationResult,
        val confirmationValidationResult: PasswordConfirmationValidationResult,

        val isLoading: Boolean,
        val requestResult: RequestResult?
    )

    // TODO: send API request
    private fun sendRequest(): Boolean {
        return Random.nextBoolean()
    }

    companion object {
        const val MIN_PASSWORD_LENGTH = 8
    }
}

