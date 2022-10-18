package com.projectx.householdtasks.presentation.event

import androidx.navigation.NavController
import com.projectx.householdtasks.presentation.adapter.SettingModel

sealed interface ProfileScreenEvent : UiEvent {
    class OnItemClicked(val item: SettingModel, navController: NavController) : ProfileScreenEvent,
        NavEvent(navController)

    class NavigateToEditProfile(navController: NavController) : ProfileScreenEvent,
        NavEvent(navController)
}