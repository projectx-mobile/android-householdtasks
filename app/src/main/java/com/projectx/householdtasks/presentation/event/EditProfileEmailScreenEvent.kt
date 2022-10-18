package com.projectx.householdtasks.presentation.event

import androidx.navigation.NavController

sealed interface EditProfileEmailScreenEvent : UiEvent {
    class NavigateToProfile(navController: NavController) : EditProfileEmailScreenEvent,
        NavEvent(navController)

    class NavBack(navController: NavController) : EditProfileEmailScreenEvent,
        NavEvent(navController)

    object HandleSaveChanges : EditProfileEmailScreenEvent
    class SetNewEmailValue(emailValue: String) : EditProfileEmailScreenEvent
    object ResetEmailError : EditProfileEmailScreenEvent
}