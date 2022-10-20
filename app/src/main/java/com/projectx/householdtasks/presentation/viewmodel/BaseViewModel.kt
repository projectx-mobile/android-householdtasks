package com.projectx.householdtasks.presentation.viewmodel

import androidx.annotation.IdRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.projectx.householdtasks.domain.use_case.BaseUseCase
import com.projectx.householdtasks.presentation.state.UiState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    class MutableUiState<Type>(initState: UiState<Type> = UiState.Loading) :
        MutableLiveData<UiState<Type>>(initState)

    protected open fun navigate(navController: NavController, @IdRes destination: Int) {
        navController.navigate(destination)
    }

    protected open fun <Type, Params> useCaseHandler(
        useCase: BaseUseCase<Type, Params>,
        params: Params,
        destination: MutableUiState<Type>
    ) {
        useCase.execute(params)
            .onStart { destination.postValue(UiState.Loading) }
            .onEach { destination.postValue(UiState.Ready(it)) }
            .catch { destination.postValue(UiState.Error(error = it)) }
            .launchIn(viewModelScope)
    }

    protected open fun <Type, Params> useCaseHandler(
        useCase: BaseUseCase<Type, Params>,
        params: Params,
        result: (Result<Type>) -> Unit
    ) {
        viewModelScope.launch {
            result(runCatching { useCase.run(params) })
        }
    }

    protected open fun <Type> Result<Type>.toUiState(): UiState<Type> {
        return fold(
            onSuccess = { UiState.Ready(it) },
            onFailure = { UiState.Error(error = it) }
        )
    }
}