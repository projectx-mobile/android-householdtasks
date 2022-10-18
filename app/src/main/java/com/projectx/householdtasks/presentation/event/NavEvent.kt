package com.projectx.householdtasks.presentation.event

import androidx.navigation.NavController

sealed class NavEvent(val navController: NavController) : UiEvent {
    class NavBack(navController: NavController) : NavEvent(navController)
}