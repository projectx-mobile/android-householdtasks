package com.projectx.householdtasks.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.projectx.householdtasks.presentation.event.NavEvent
import com.projectx.householdtasks.presentation.event.UiEvent
import com.projectx.householdtasks.presentation.state.UiState

abstract class BaseViewModel<State, Event> : ViewModel() where State : Any, Event : UiEvent {

    open val state: LiveData<UiState<State>> = object : LiveData<UiState<State>>() {}

    open fun onEvent(event: Event) {
        when (event) {
            is NavEvent.NavBack -> event.navController.navigateUp()
        }
    }
}