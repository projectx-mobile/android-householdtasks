package com.projectx.householdtasks.presentation.viewmodel

import com.projectx.householdtasks.R
import com.projectx.householdtasks.presentation.FamilyMember
import com.projectx.householdtasks.presentation.adapter.NotificationSwitchersModel
import com.projectx.householdtasks.presentation.adapter.notificationSwitchesList

class NotificationViewModel : BaseViewModel() {

    fun getSwitchersList(): List<NotificationSwitchersModel> {
        return notificationSwitchesList
    }

    fun handleClick(item: NotificationSwitchersModel) {
        when (item) {
            NotificationSwitchersModel.NEW_TASK_STATUS -> {}
            NotificationSwitchersModel.AWARDS_REQUEST -> {}
            NotificationSwitchersModel.TASKS_REQUEST -> {}
        }
    }

    fun getFamilyList(): List<FamilyMember> {
        return listOf(
            FamilyMember("Алиса", null),
            FamilyMember("Борис", R.drawable.ic_avata),
            FamilyMember("Алиса", null),
            FamilyMember("Борис", null),
            FamilyMember("Алиса", null),
            FamilyMember("Борис", null),
            FamilyMember("Алиса", null),
            FamilyMember("Борис", null),
            FamilyMember("Приглашен", R.drawable.button_invited_person),
        )
    }
}