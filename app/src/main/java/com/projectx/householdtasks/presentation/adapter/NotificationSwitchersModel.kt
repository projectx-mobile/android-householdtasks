package com.projectx.householdtasks.presentation.adapter

import com.projectx.householdtasks.R

enum class NotificationSwitchersModel(
    val icon: Int,
    val title: Int
) {
    NEW_TASK_STATUS(R.drawable.ic_new_task_state, R.string.notification_new_tasks_status),
    TASKS_REQUEST(R.drawable.ic_request_for_tasks, R.string.notifications_tasks_request),
    AWARDS_REQUEST(R.drawable.ic_awards_black, R.string.notifications_awards_request),
}

val notificationSwitchesList =
    listOf(
        NotificationSwitchersModel.NEW_TASK_STATUS,
        NotificationSwitchersModel.TASKS_REQUEST, NotificationSwitchersModel.AWARDS_REQUEST
    )