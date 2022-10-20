package com.projectx.householdtasks.domain.use_case

import com.projectx.householdtasks.R
import com.projectx.householdtasks.presentation.FamilyMember

class GetFamilyMembersUseCase : BaseUseCase<List<FamilyMember>, Unit>() {
    override suspend fun run(params: Unit): List<FamilyMember> {
        return listOf(
            FamilyMember("Добавить", R.drawable.selector_add_button),
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