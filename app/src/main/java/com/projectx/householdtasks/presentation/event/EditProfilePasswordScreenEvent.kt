package com.projectx.householdtasks.presentation.event

import androidx.navigation.NavController

sealed interface EditProfilePasswordScreenEvent : UiEvent {
    class SetCurrentPasswordValue(val password: String) : EditProfilePasswordScreenEvent
    class SetNewPasswordValue(val password: String) : EditProfilePasswordScreenEvent
    class SetPasswordConfirmationValue(val password: String) : EditProfilePasswordScreenEvent
    object ResetCurrentPasswordError : EditProfilePasswordScreenEvent
    object ResetNewPasswordError : EditProfilePasswordScreenEvent
    object ResetPasswordConfirmationError : EditProfilePasswordScreenEvent
    object HandleSaveChanges : EditProfilePasswordScreenEvent
    class NavBack(navController: NavController) : EditProfilePasswordScreenEvent,
        NavEvent(navController)
}