package com.projectx.householdtasks.presentation.event

import androidx.navigation.NavController

sealed interface ChooseLoginTypeScreenEvent : UiEvent {
    class LoginWithGoogle(navController: NavController) : ChooseLoginTypeScreenEvent, NavEvent(navController)
    class LoginWithEmail(navController: NavController) : ChooseLoginTypeScreenEvent, NavEvent(navController)
    class CreateAccount(navController: NavController) : ChooseLoginTypeScreenEvent, NavEvent(navController)
}
