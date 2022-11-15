package com.projectx.auth.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.projectx.auth.presentation.fragment.CreateAccountFragmentDirections
import com.projectx.common.presentation.navigation.NavEvent
import com.projectx.common.presentation.state.UiState
import com.projectx.common.presentation.viewmodel.BaseViewModel
import kotlin.random.Random

class CreateAccountViewModel: BaseViewModel() {

    val state = MutableUiState(
        UiState.Ready(
            CreateAccountUiState(
                MutableLiveData(NameValidationResult.OK),
                MutableLiveData(PasswordValidationResult.OK)
            )
        )
    )

    fun onFinishRegistrationButtonClick() {
        handleChanges()
    }

    fun onNameTextChanged(name: String) {
        setNameValue(name)
        resetNameError()
    }

    fun onPasswordTextChanged(password: String) {
        setPasswordValue(password)
        resetPasswordError()
    }

    fun onPasswordConfirmTextChanged(passwordConfirm: String) {
        setPasswordConfirmValue(passwordConfirm)
        resetPasswordError()
    }

    fun onRequestSuccess() {
        navigate(NavEvent.To(CreateAccountFragmentDirections.actionCreateAccountFragmentToCreateAccountNotificationsFragment()))
    }

    private fun setNameValue(name: String) {
        when (state.value) {
            is UiState.Ready -> (state.value as UiState.Ready<CreateAccountUiState>).data.name.value =
                name
            else -> {}
        }
    }

    private fun setPasswordValue(password: String) {
        when (state.value) {
            is UiState.Ready -> (state.value as UiState.Ready<CreateAccountUiState>).data.password.value =
                password
            else -> {}
        }
    }

    private fun setPasswordConfirmValue(passwordConfirm: String) {
        when (state.value) {
            is UiState.Ready -> (state.value as UiState.Ready<CreateAccountUiState>).data.passwordConfirm.value =
                passwordConfirm
            else -> {}
        }
    }

    private fun resetNameError() {
        when (state.value) {
            is UiState.Ready -> (state.value as UiState.Ready<CreateAccountUiState>).data.nameValidationResult.value =
                NameValidationResult.OK
            else -> {}
        }
    }

    private fun resetPasswordError() {
        when (state.value) {
            is UiState.Ready -> (state.value as UiState.Ready<CreateAccountUiState>).data.passwordValidationResult.value =
                PasswordValidationResult.OK
            else -> {}
        }
    }

    fun isFinishRegistrationButtonEnabled(): Boolean {
        return when (state.value) {
            is UiState.Ready -> {
                ((state.value as UiState.Ready<CreateAccountUiState>).data.name.value?.isNotEmpty() == true)
                        && ((state.value as UiState.Ready<CreateAccountUiState>).data.password.value?.isNotEmpty() == true)
                        && ((state.value as UiState.Ready<CreateAccountUiState>).data.passwordConfirm.value?.isNotEmpty() == true)
            }
            else -> false
        }
    }

    private fun validateName(): NameValidationResult {
        return when (state.value) {
            is UiState.Ready -> {
                val newName =
                    (state.value as UiState.Ready<CreateAccountUiState>).data.name.value ?: ""
                when {
                    !nameContainsLetter(newName) -> NameValidationResult.NO_LETTERS_ERROR
                    !nameHasInvalidCharacter(newName) -> NameValidationResult.INVALID_CHARACTER_ERROR
                    !isNameLengthValid(newName) -> NameValidationResult.LENGTH_ERROR
                    else -> NameValidationResult.OK
                }
            }
            else -> NameValidationResult.OK
        }
    }

    private fun validatePassword(): PasswordValidationResult {
        return when (state.value) {
            is UiState.Ready -> {
                val password =
                    (state.value as UiState.Ready<CreateAccountUiState>).data.password.value ?: ""
                val passwordConfirm =
                    (state.value as UiState.Ready<CreateAccountUiState>).data.passwordConfirm.value ?: ""
                when {
                    !passwordHasInvalidCharacter(password) -> PasswordValidationResult.FORMAT_ERROR
                    !isPasswordLengthValid(password) -> PasswordValidationResult.LENGTH_ERROR
                    !isPasswordConfirmed(password, passwordConfirm) -> PasswordValidationResult.CONFIRM_ERROR
                    else -> PasswordValidationResult.OK
                }
            }
            else -> PasswordValidationResult.OK
        }
    }

    private fun nameContainsLetter(name: String): Boolean {
        return name.toCharArray().any { it.isLetter() }
    }

    private fun nameHasInvalidCharacter(name: String): Boolean {
        return name.toCharArray().all { it.isLetter() || it.isDigit() }
    }

    private fun passwordHasInvalidCharacter(password: String): Boolean {
        return password.toCharArray().all { it.isLetter() && it.isDigit() }
    }

    private fun isNameLengthValid(name: String): Boolean {
        return name.length <= NAME_MAX_LENGTH
    }

    private fun isPasswordLengthValid(password: String): Boolean {
        return (password.length <= PASSWORD_MAX_LENGTH) && (password.length >= PASSWORD_MIN_LENGTH)
    }

    private fun isPasswordConfirmed(password: String, passwordConfirm: String): Boolean {
        return password == passwordConfirm
    }

    private fun handleChanges() {
        val nameValidationResult = validateName()
        val passwordValidationResult = validatePassword()
        when (state.value) {
            is UiState.Ready -> {
                (state.value as UiState.Ready<CreateAccountUiState>).apply {
                    data.nameValidationResult.value = nameValidationResult
                    data.passwordValidationResult.value = passwordValidationResult
                    data.requestResult.value = when {
                        nameValidationResult != NameValidationResult.OK -> null
                        passwordValidationResult != PasswordValidationResult.OK -> null
                        else -> {
                            val requestSucceeded = sendRequest()
                            if (requestSucceeded) {
                                RequestResult.SUCCESS
                            } else {
                                RequestResult.ERROR
                            }
                        }
                    }
                }
            }
            else -> {}
        }
    }

    // TODO: send API request
    private fun sendRequest(): Boolean {
        return Random.nextBoolean()
    }

    fun backToOnBoardingFragment() {
        navigate(NavEvent.To(CreateAccountFragmentDirections.actionCreateAccountFragmentToOnBoardingFragment()))
    }

    fun goToNotificationPermission() {
        navigate(NavEvent.To(CreateAccountFragmentDirections.actionCreateAccountFragmentToCreateAccountNotificationsFragment()))
    }

    data class CreateAccountUiState(
        val nameValidationResult: MutableLiveData<NameValidationResult>,
        val passwordValidationResult: MutableLiveData<PasswordValidationResult>,
        val name: MutableLiveData<String> = MutableLiveData<String>(""),
        val password: MutableLiveData<String> = MutableLiveData<String>(""),
        val passwordConfirm: MutableLiveData<String> = MutableLiveData<String>(""),
        val requestResult: MutableLiveData<RequestResult?> = MutableLiveData(null)
    )

    companion object {
        const val NAME_MAX_LENGTH = 20
        const val PASSWORD_MAX_LENGTH = 8
        const val PASSWORD_MIN_LENGTH = 16

        enum class NameValidationResult {
            OK,
            LENGTH_ERROR,
            NO_LETTERS_ERROR,
            INVALID_CHARACTER_ERROR
        }

        enum class PasswordValidationResult {
            OK,
            LENGTH_ERROR,
            FORMAT_ERROR,
            CONFIRM_ERROR
        }

        enum class RequestResult {
            SUCCESS,
            ERROR
        }

    }
}