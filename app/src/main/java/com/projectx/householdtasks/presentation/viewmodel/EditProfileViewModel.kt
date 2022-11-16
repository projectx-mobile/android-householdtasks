package com.projectx.householdtasks.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.distinctUntilChanged
import com.projectx.householdtasks.presentation.NameValidationResult
import com.projectx.householdtasks.presentation.RequestResult
import kotlin.random.Random


class EditProfileViewModel : BaseViewModel() {
    private val _newName = MutableLiveData("")
    val newName: LiveData<String> = distinctUntilChanged(_newName)
    private val _uiState: MutableLiveData<UiState> =
        MutableLiveData(UiState(NameValidationResult.OK, false, null))
    val uiState get() = _uiState

    fun setNameValue(name: String) {
        _newName.value = name
    }

    fun resetNameError() {
        _uiState.postValue(_uiState.value!!.copy(nameValidationResult = NameValidationResult.OK))
    }

    fun isSaveButtonEnabled(): Boolean {
        return newName.value!!.isNotEmpty()
    }

    private fun validateName(): NameValidationResult {
        if (!nameContainsLetter(newName.value!!)) return NameValidationResult.NoLettersError
        if (!nameHasInvalidCharacter(newName.value!!)) return NameValidationResult.InvalidCharacterError
        if (!isNameLengthValid(newName.value!!)) return NameValidationResult.LengthError
        return NameValidationResult.OK
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

        if (nameValidationResult != NameValidationResult.OK) {
            _uiState.postValue(UiState(nameValidationResult, false, null))
            return
        }

        val requestSucceeded = sendRequest()
        if (requestSucceeded) {
            _uiState.postValue(UiState(nameValidationResult, false, RequestResult.Success))
        } else {
            _uiState.postValue(
                UiState(
                    nameValidationResult,
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
        val nameValidationResult: NameValidationResult,

        val isLoading: Boolean,
        val requestResult: RequestResult?
    )

    companion object {
        const val NAME_MAX_LENGTH = 20
    }
}
