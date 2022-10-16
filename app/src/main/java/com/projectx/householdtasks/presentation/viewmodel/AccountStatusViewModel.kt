package com.projectx.householdtasks.presentation.viewmodel

import com.projectx.householdtasks.R
import com.projectx.householdtasks.presentation.FamilyMember

class AccountStatusViewModel : BaseViewModel() {

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