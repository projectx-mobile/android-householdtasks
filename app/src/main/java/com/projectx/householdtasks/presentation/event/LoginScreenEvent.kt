package com.projectx.householdtasks.presentation.event

import androidx.navigation.NavController

sealed interface LoginScreenEvent : UiEvent {
    class EmailValidate(val email: String) : LoginScreenEvent
    class PasswordValidate(val password: String) : LoginScreenEvent
    class NavigateToProfile(navController: NavController) : LoginScreenEvent, NavEvent(navController)
    class NavBack(navController: NavController) : LoginScreenEvent, NavEvent(navController)
    object HandleSaveChanges : LoginScreenEvent
}