package com.projectx.householdtasks.presentation.event

import androidx.navigation.NavController

sealed interface OnBoardingScreenEvent : UiEvent {
    class OnImageSelected(val position: Int) : OnBoardingScreenEvent
    class OnImageUnselected(val position: Int) : OnBoardingScreenEvent
    class NavigateToChooseLoginType(navController: NavController) : OnBoardingScreenEvent, NavEvent(navController)
}