package com.projectx.householdtasks.presentation.viewmodel

import androidx.lifecycle.LiveData
import com.projectx.householdtasks.R
import com.projectx.householdtasks.presentation.FamilyMember
import com.projectx.householdtasks.presentation.event.NavEvent
import com.projectx.householdtasks.presentation.state.UiState

class AccountStatusViewModel : BaseViewModel<List<FamilyMember>, NavEvent>() {

    override val state =
        object : LiveData<UiState<List<FamilyMember>>>(UiState.Ready(createFamilyList())) {}

    fun createFamilyList(): List<FamilyMember> {
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