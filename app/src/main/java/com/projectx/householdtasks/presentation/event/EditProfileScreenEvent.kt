package com.projectx.householdtasks.presentation.event

import androidx.navigation.NavController

sealed interface EditProfileScreenEvent : UiEvent {
    class NavBack(navController: NavController) : EditProfileScreenEvent, NavEvent(navController)
    class NavigateToProfile(navController: NavController) : EditProfileScreenEvent, NavEvent(navController)
    class SetNameValue(val name: String) : EditProfileScreenEvent
    object ResetNameError : EditProfileScreenEvent
    object HandleSaveChanges : EditProfileScreenEvent
}