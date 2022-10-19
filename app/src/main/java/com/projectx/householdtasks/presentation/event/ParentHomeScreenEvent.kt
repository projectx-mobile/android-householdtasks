package com.projectx.householdtasks.presentation.event

import androidx.navigation.NavController

sealed interface ParentHomeScreenEvent : UiEvent {
    class NavigateToAllUpdates(navController: NavController) : ParentHomeScreenEvent, NavEvent(navController)
    class NavBack(navController: NavController) : ParentHomeScreenEvent, NavEvent(navController)
}