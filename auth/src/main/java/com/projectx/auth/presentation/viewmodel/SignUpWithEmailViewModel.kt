package com.projectx.auth.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.projectx.auth.presentation.fragment.CreateAccountFragmentDirections
import com.projectx.auth.presentation.fragment.SignUpWithEmailFragmentDirections
import com.projectx.common.ProfileNavGraphDirections
import com.projectx.common.domain.use_case.ValidateEmailUseCase
import com.projectx.common.presentation.navigation.NavEvent
import com.projectx.common.presentation.viewmodel.BaseViewModel
import com.projectx.common.presentation.viewmodel.EditProfileEmailViewModel
import com.projectx.common.presentation.viewmodel.EditProfileViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.random.Random

class SignUpWithEmailViewModel(
    private val validateEmailUseCase: ValidateEmailUseCase,
): BaseViewModel() {

    private var _email = MutableLiveData("")
    var email: LiveData<String> =  Transformations.distinctUntilChanged(_email)

    private val _uiState: MutableLiveData<UiState> = MutableLiveData(
        UiState(ValidateEmailUseCase.EmailValidationResult.OK, false, null)
    )
    val uiState: LiveData<UiState> get() = _uiState

    val isContinueButtonEnabled = MediatorLiveData<Boolean>().apply {
        addSource(email) {
            value = isContinueButtonEnabled()
        }
    }

    private fun isContinueButtonEnabled(): Boolean {
        return email.value!!.isNotEmpty()
    }

    fun setEmailValue(email: String) {
        _email.value = email
    }

    fun resetEmailError() {
        _uiState.postValue(_uiState.value!!.copy(emailValidationResult = ValidateEmailUseCase.EmailValidationResult.OK))
    }

    fun onRequestSuccess() {
        navigate(NavEvent.To(SignUpWithEmailFragmentDirections.actionSignUpWithEmailFragmentToSignUpEmailConfirmationFragment()))
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
                            if (requestSucceeded) RequestResult.SUCCESS else RequestResult.REQUESTFAILEDERROR
                        )
                    )
                }
                else -> {
                    _uiState.postValue(UiState(it))
                }
            }
        }
    }

    //  TODO: request
    private fun sendRequest(): Boolean {
        return Random.nextBoolean()
    }

    private fun validateEmail(result: (ValidateEmailUseCase.EmailValidationResult) -> Unit) {
        useCaseHandler(validateEmailUseCase, email.value ?: "") {
            result(it.getOrDefault(ValidateEmailUseCase.EmailValidationResult.ERROR))
        }
    }

    data class UiState(
        val emailValidationResult: ValidateEmailUseCase.EmailValidationResult = ValidateEmailUseCase.EmailValidationResult.OK,
        val isLoading: Boolean = false,
        val requestResult: RequestResult? = null
    )

    enum class RequestResult{
        SUCCESS,
        REQUESTFAILEDERROR
    }

}