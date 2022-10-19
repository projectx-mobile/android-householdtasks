package com.projectx.householdtasks.presentation.event

import androidx.navigation.NavController

sealed interface InviteUserByEmailScreenEvent : UiEvent {
    class SetEmailValue(val email: String) : InviteUserByEmailScreenEvent
    object ResetEmailError : InviteUserByEmailScreenEvent
    object HandleSaveChanges : InviteUserByEmailScreenEvent
    class NavigateToProfile(navController: NavController) : InviteUserByEmailScreenEvent, NavEvent(navController)
    class NavBack(navController: NavController) : InviteUserByEmailScreenEvent, NavEvent(navController)
}