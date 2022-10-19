package com.projectx.householdtasks.presentation.viewmodel

import androidx.lifecycle.LiveData
import com.projectx.householdtasks.R
import com.projectx.householdtasks.presentation.FamilyMember
import com.projectx.householdtasks.presentation.adapter.NotificationSwitchersModel
import com.projectx.householdtasks.presentation.adapter.notificationSwitchesList
import com.projectx.householdtasks.presentation.event.NotificationScreenEvent
import com.projectx.householdtasks.presentation.state.UiState

class NotificationViewModel : BaseViewModel<NotificationSharedViewModel.NotificationScreenUiState, NotificationScreenEvent>() {

    override val state = object : LiveData<UiState<NotificationSharedViewModel.NotificationScreenUiState>>(
        UiState.Ready(
            NotificationSharedViewModel.NotificationScreenUiState(
                notificationSwitchesList,
                getFamilyList()
            )
        )
    ) {}

    override fun onEvent(event: NotificationScreenEvent) {
        when (event) {
            is NotificationScreenEvent.OnItemClicked -> handleClick(event.item)
            else -> super.onEvent(event)
        }
    }

    private fun handleClick(item: NotificationSwitchersModel) {
        when (item) {
            NotificationSwitchersModel.NEW_TASK_STATUS -> {}
            NotificationSwitchersModel.AWARDS_REQUEST -> {}
            NotificationSwitchersModel.TASKS_REQUEST -> {}
        }
    }

    private fun getFamilyList(): List<FamilyMember> {
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