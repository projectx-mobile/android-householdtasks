package com.projectx.householdtasks.presentation.event

import androidx.navigation.NavController
import com.projectx.householdtasks.presentation.adapter.SettingModel

sealed interface SupportScreenEvent : UiEvent {
    class OnItemClicked(val item: SettingModel, navController: NavController) : SupportScreenEvent, NavEvent(navController)
    class NavBack(navController: NavController) : SupportScreenEvent, NavEvent(navController)
}