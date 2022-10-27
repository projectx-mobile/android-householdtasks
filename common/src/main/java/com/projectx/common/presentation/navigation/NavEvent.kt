package com.projectx.common.presentation.navigation

import androidx.navigation.NavDirections

sealed interface NavEvent {
    data class To(val directions: NavDirections) : NavEvent
    object Up : NavEvent
    object Back : NavEvent
    object BackToRoot : NavEvent
}