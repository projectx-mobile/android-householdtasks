package com.projectx.common.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.projectx.common.ProfileNavGraphDirections
import com.projectx.common.presentation.navigation.NavEvent
import com.projectx.common.presentation.state.UiState
import kotlin.random.Random

class EditProfileViewModel : BaseViewModel() {

    val state = MutableUiState(
        UiState.Ready(EditProfileUiState(MutableLiveData(NameValidationResult.OK)))
    )

    init {
        //TODO: set current name
        setNameValue("Марго")
    }

    fun onToolbarClick() {
        navigate(NavEvent.Up)
    }

    fun onSaveChangesButtonClick() {
        handleSaveChanges()
    }

    fun onNameTextChanged(name: String) {
        setNameValue(name)
        resetNameError()
    }

    fun onRequestSuccess() {
        navigate(NavEvent.To(ProfileNavGraphDirections.actionGlobalProfileFragment()))
    }

    private fun setNameValue(name: String) {
        when (state.value) {
            is UiState.Ready -> (state.value as UiState.Ready<EditProfileUiState>).data.newName.value =
                name
            else -> {}
        }
    }

    private fun resetNameError() {
        when (state.value) {
            is UiState.Ready -> (state.value as UiState.Ready<EditProfileUiState>).data.nameValidationResult.value =
                NameValidationResult.OK
            else -> {}
        }
    }

    fun isSaveButtonEnabled(): Boolean {
        return when (state.value) {
            is UiState.Ready -> (state.value as UiState.Ready<EditProfileUiState>).data.newName.value?.isNotEmpty()
                ?: false
            else -> false
        }
    }

    private fun validateName(): NameValidationResult {
        return when (state.value) {
            is UiState.Ready -> {
                val newName =
                    (state.value as UiState.Ready<EditProfileUiState>).data.newName.value ?: ""
                when {
                    !nameContainsLetter(newName) -> NameValidationResult.NoLettersError
                    !nameHasInvalidCharacter(newName) -> NameValidationResult.InvalidCharacterError
                    !isNameLengthValid(newName) -> NameValidationResult.LengthError
                    else -> NameValidationResult.OK
                }
            }
            else -> NameValidationResult.OK
        }
    }

    private fun nameContainsLetter(name: String): Boolean {
        return name.toCharArray().any { it.isLetter() }
    }

    private fun nameHasInvalidCharacter(name: String): Boolean {
        return name.toCharArray().all { it.isLetter() || it.isDigit() }
    }

    private fun isNameLengthValid(name: String): Boolean {
        return name.length <= NAME_MAX_LENGTH
    }

    private fun handleSaveChanges() {
        val nameValidationResult = validateName()
        when (state.value) {
            is UiState.Ready -> {
                (state.value as UiState.Ready<EditProfileUiState>).apply {
                    data.nameValidationResult.value = nameValidationResult
                    data.requestResult.value = when {
                        nameValidationResult != NameValidationResult.OK -> null
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

    // TODO: send API request
    private fun sendRequest(): Boolean {
        return Random.nextBoolean()
    }

    data class EditProfileUiState(
        val nameValidationResult: MutableLiveData<NameValidationResult>,
        val newName: MutableLiveData<String> = MutableLiveData<String>(""),
        val requestResult: MutableLiveData<RequestResult?> = MutableLiveData(null)
    )

    companion object {
        const val NAME_MAX_LENGTH = 20

        sealed class NameValidationResult {
            object OK : NameValidationResult()
            object LengthError: NameValidationResult()
            object NoLettersError: NameValidationResult()
            object InvalidCharacterError: NameValidationResult()
        }

        sealed class LoginEmailResult {
            object OK : LoginEmailResult()
            object Empty : LoginEmailResult()
            object InvalidEmailError : LoginEmailResult()
        }
        sealed class LoginPasswordResult {
            object OK : LoginPasswordResult()
            object Empty : LoginPasswordResult()
            object LengthError: LoginPasswordResult()
        }

        sealed class EmailValidationResult {
            object OK : EmailValidationResult()
            object InvalidEmailError: EmailValidationResult()
        }

        sealed class CurrentPasswordValidationResult {
            object OK : CurrentPasswordValidationResult()
            object LengthError: CurrentPasswordValidationResult()
            object InvalidPasswordError: CurrentPasswordValidationResult()
        }

        sealed class NewPasswordValidationResult {
            object OK : NewPasswordValidationResult()
            object LengthError: NewPasswordValidationResult()
        }

        sealed class PasswordConfirmationValidationResult {
            object OK : PasswordConfirmationValidationResult()
            object LengthError: PasswordConfirmationValidationResult()
            object PasswordsMismatchError: PasswordConfirmationValidationResult()
        }

        sealed class RequestResult {
            object Success: RequestResult()
            object RequestFailedError: RequestResult()
        }


        data class EditProfileResult(
            val nameValidationResult: NameValidationResult,
            val emailValidationResult: EmailValidationResult,
            val currentPasswordValidationResult: CurrentPasswordValidationResult,
            val newPasswordValidationResult: NewPasswordValidationResult,
            val passwordConfirmationValidationResult: PasswordConfirmationValidationResult,
            val requestResult: RequestResult
        )
    }
}
