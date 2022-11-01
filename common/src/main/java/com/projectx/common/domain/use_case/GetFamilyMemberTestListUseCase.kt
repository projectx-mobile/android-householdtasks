package com.projectx.common.domain.use_case

import com.projectx.common.presentation.model.FamilyMemberTest

class GetFamilyMemberTestListUseCase : BaseUseCase<List<FamilyMemberTest>, Unit>() {
    override suspend fun run(params: Unit): List<FamilyMemberTest> = listOf(
        FamilyMemberTest("John", 5, 1),
        FamilyMemberTest("Алиса", 3, 2),
    )
}