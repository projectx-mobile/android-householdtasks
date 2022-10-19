package com.projectx.householdtasks.presentation.event

import androidx.navigation.NavController

sealed interface InviteUserScreenEvent : UiEvent {
    class NavigateToInviteUserByEmail(navController: NavController) : InviteUserScreenEvent, NavEvent(navController)
    class NavBack(navController: NavController) : InviteUserScreenEvent, NavEvent(navController)
}