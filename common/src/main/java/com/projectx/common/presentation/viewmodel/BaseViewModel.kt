package com.projectx.common.presentation.viewmodel

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectx.common.domain.use_case.BaseUseCase
import com.projectx.common.presentation.navigation.NavEvent
import com.projectx.common.presentation.navigation.NavigationObserver
import com.projectx.common.presentation.state.UiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    class MutableUiState<Type>(initState: UiState<Type> = UiState.Loading) :
        MutableLiveData<UiState<Type>>(initState)

    private val navEventChannel = Channel<NavEvent>()
    val navEventFlow: Flow<NavEvent> = navEventChannel.receiveAsFlow()

    fun navigate(navEvent: NavEvent) = viewModelScope.launch {
        navEventChannel.send(navEvent)
    }

    open fun navigateTo(event: NavEvent.To, fragment: Fragment) =
        NavigationObserver.navigateTo(event, fragment)

    open fun navigateBack(fragment: Fragment) = NavigationObserver.navigateBack(fragment)

    open fun navigateBackToRoot(fragment: Fragment) =
        NavigationObserver.navigateBackToRoot(fragment)

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