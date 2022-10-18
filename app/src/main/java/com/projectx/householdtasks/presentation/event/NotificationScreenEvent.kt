package com.projectx.householdtasks.presentation.event

import androidx.navigation.NavController
import com.projectx.householdtasks.presentation.adapter.NotificationSwitchersModel

sealed interface NotificationScreenEvent : UiEvent {
    class OnItemClicked(val item: NotificationSwitchersModel) : NotificationScreenEvent
    class NavBack(navController: NavController) : NotificationScreenEvent, NavEvent(navController)
}