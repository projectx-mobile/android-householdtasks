package com.projectx.householdtasks.presentation.state

import com.projectx.householdtasks.presentation.FamilyMember
import com.projectx.householdtasks.presentation.adapter.NotificationSwitchersModel

data class NotificationScreenUiState(
    val switchersList: List<NotificationSwitchersModel>,
    val familyList: List<FamilyMember>
)
