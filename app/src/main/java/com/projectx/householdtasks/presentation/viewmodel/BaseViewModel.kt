package com.projectx.householdtasks.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectx.householdtasks.domain.use_case.BaseUseCase
import com.projectx.householdtasks.presentation.event.NavEvent
import com.projectx.householdtasks.presentation.event.UiEvent
import com.projectx.householdtasks.presentation.state.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

abstract class BaseViewModel<State, Event> : ViewModel() where State : Any, Event : UiEvent {

    open val state: LiveData<UiState<State>> = object : LiveData<UiState<State>>() {}

    open fun onEvent(event: Event) {
        when (event) {
            is NavEvent.NavBack -> event.navController.navigateUp()
        }
    }

    fun <Type, Params> useCaseHandler(useCase: BaseUseCase<Type, Params>, params: Params, result: (Type) -> Unit) where Type : Any? {
        viewModelScope.launch(Dispatchers.IO) {
            result(useCase.run(params))
        }
    }
}