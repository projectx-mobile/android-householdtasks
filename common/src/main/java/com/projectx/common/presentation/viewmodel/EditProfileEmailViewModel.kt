package com.projectx.common.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.projectx.common.ProfileNavGraphDirections
import com.projectx.common.domain.use_case.ValidateEmailUseCase
import com.projectx.common.domain.use_case.ValidatePasswordUseCase
import com.projectx.common.presentation.navigation.NavEvent
import com.projectx.common.presentation.viewmodel.EditProfileViewModel.Companion.RequestResult
import kotlin.random.Random

class EditProfileEmailViewModel(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : BaseViewModel() {

    private val _newEmail = MutableLiveData("")
    val newEmail: LiveData<String> = Transformations.distinctUntilChanged(_newEmail)

    private val _uiState: MutableLiveData<UiState> = MutableLiveData(UiState())
    val uiState: LiveData<UiState> get() = _uiState

    val isContinueButtonEnabled = MediatorLiveData<Boolean>().apply {
        addSource(newEmail) {
            value = it.isNotEmpty()
        }
    }
    val isSaveButtonEnabled = isContinueButtonEnabled

    fun setNewEmailValue(newEmail: String) {
        _newEmail.value = newEmail
    }

    fun resetEmailError() {
        _uiState.postValue(_uiState.value?.copy(emailValidationResult = ValidateEmailUseCase.EmailValidationResult.OK))
    }

    fun onRequestSuccess() {
        navigate(NavEvent.To(ProfileNavGraphDirections.actionGlobalProfileFragment()))
    }

    fun onToolbarArrowClick() {
        navigate(NavEvent.Up)
    }

    fun handleSaveChanges() {
        validateEmail {
            when (it) {
                ValidateEmailUseCase.EmailValidationResult.OK -> {
                    val requestSucceeded = sendRequest()
                    _uiState.postValue(
                        UiState(
                            it,
                            false,
                            if (requestSucceeded) RequestResult.Success else RequestResult.RequestFailedError
                        )
                    )
                }
                else -> {
                    _uiState.postValue(UiState(it))
                }
            }
        }
    }

    private fun validateEmail(result: (ValidateEmailUseCase.EmailValidationResult) -> Unit) {
        useCaseHandler(validateEmailUseCase, newEmail.value ?: "") {
            result(it.getOrDefault(ValidateEmailUseCase.EmailValidationResult.ERROR))
        }
    }

    //  TODO: request
    private fun sendRequest(): Boolean {
        return Random.nextBoolean()
    }

    data class UiState(
        val emailValidationResult: ValidateEmailUseCase.EmailValidationResult = ValidateEmailUseCase.EmailValidationResult.OK,
        val isLoading: Boolean = false,
        val requestResult: RequestResult? = null
    )
}
