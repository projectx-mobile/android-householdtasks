package com.projectx.householdtasks.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.projectx.householdtasks.R
import com.projectx.householdtasks.presentation.NameValidationResult
import com.projectx.householdtasks.presentation.RequestResult
import com.projectx.householdtasks.presentation.event.EditProfileScreenEvent
import com.projectx.householdtasks.presentation.state.UiState
import kotlin.random.Random

class EditProfileViewModel :
    BaseViewModel<EditProfileViewModel.EditProfileUiState, EditProfileScreenEvent>() {

    override val state = MutableLiveData<UiState<EditProfileUiState>>(
        UiState.Ready(EditProfileUiState(MutableLiveData(NameValidationResult.OK)))
    )

    override fun onEvent(event: EditProfileScreenEvent) {
        when (event) {
            is EditProfileScreenEvent.NavigateToProfile -> event.navController.navigate(R.id.profileFragment)
            is EditProfileScreenEvent.SetNameValue -> setNameValue(event.name)
            is EditProfileScreenEvent.ResetNameError -> resetNameError()
            else -> super.onEvent(event)
        }
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

    fun handleSaveChanges() {
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
    }
}
