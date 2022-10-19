package com.projectx.householdtasks.presentation.event

import androidx.navigation.NavController

sealed interface LoginScreenEvent : UiEvent {
    class SetEmailValue(val email: String) : LoginScreenEvent
    class SetPasswordValue(val password: String) : LoginScreenEvent
    class NavigateToProfile(navController: NavController) : LoginScreenEvent, NavEvent(navController)
    class NavBack(navController: NavController) : LoginScreenEvent, NavEvent(navController)
    object HandleSaveChanges : LoginScreenEvent
    object ResetEmailError : LoginScreenEvent
    object ResetPasswordError : LoginScreenEvent
}