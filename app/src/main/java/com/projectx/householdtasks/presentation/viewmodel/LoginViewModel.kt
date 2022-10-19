package com.projectx.householdtasks.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.projectx.householdtasks.R
import com.projectx.householdtasks.domain.use_case.ValidateEmailUseCase
import com.projectx.householdtasks.domain.use_case.ValidatePasswordUseCase
import com.projectx.householdtasks.presentation.LoginEmailResult
import com.projectx.householdtasks.presentation.LoginPasswordResult
import com.projectx.householdtasks.presentation.RequestResult
import com.projectx.householdtasks.presentation.event.LoginScreenEvent
import com.projectx.householdtasks.presentation.state.UiState
import kotlin.random.Random

class LoginViewModel(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : BaseViewModel<LoginViewModel.LoginScreenUiState, LoginScreenEvent>() {
    //TODO: REFACTOR LOGIC
    override val state = MutableLiveData<UiState<LoginScreenUiState>>(
        UiState.Ready(LoginScreenUiState())
    )

    override fun onEvent(event: LoginScreenEvent) {
        when (event) {
            is LoginScreenEvent.EmailValidate -> {
                useCaseHandler(validateEmailUseCase, event.email, result = { result ->
                    UiState.process(state.value, onReady = {
                        state.postValue(
                            UiState.Ready(
                                it.copy(loginEmailResult = result)
                            )
                        )
                    })
                })
            }
            is LoginScreenEvent.PasswordValidate -> {
                useCaseHandler(validatePasswordUseCase, event.password, result = { result ->
                    UiState.process(state.value, onReady = {
                        state.postValue(
                            UiState.Ready(
                                it.copy(loginPasswordResult = result)
                            )
                        )
                    })
                })
            }
            is LoginScreenEvent.NavigateToProfile -> event.navController.navigate(R.id.profileFragment)
            else -> super.onEvent(event)
        }
    }

    private fun isValid(state: LoginScreenUiState): Boolean {
        return state.loginEmailResult == LoginEmailResult.OK && state.loginPasswordResult == LoginPasswordResult.OK
    }

    fun handleSaveChanges() {
        UiState.process(state.value, onReady = {
            val requestSucceeded = sendRequest()
            val newState = it.copy(
                requestResult = when {
                    !isValid(it) -> null
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

    data class LoginScreenUiState(
        val loginEmailResult: LoginEmailResult = LoginEmailResult.Empty,
        val loginPasswordResult: LoginPasswordResult = LoginPasswordResult.Empty,
        val requestResult: RequestResult? = null
    )

    companion object {
        const val START_PARENT_LINK = 19
        const val END_PARENT_LINK = 39
        const val START_CHILD_LINK = 0
        const val END_CHILD_LINK = 21
    }
}