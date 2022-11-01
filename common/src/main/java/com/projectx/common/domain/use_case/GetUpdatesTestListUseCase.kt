package com.projectx.common.domain.use_case

import com.projectx.common.presentation.model.UpdatesTest

class GetUpdatesTestListUseCase : BaseUseCase<List<UpdatesTest>, Unit>() {
    override suspend fun run(params: Unit): List<UpdatesTest> = listOf(
        UpdatesTest("Алиса создала новую задачу"),
        UpdatesTest("Алиса выполнила задачу"),
        UpdatesTest("Борис выбрал награду"),
        UpdatesTest("Борис выполнил задачу"),
        UpdatesTest("Борис выполнил задачу"),
        UpdatesTest("Алиса создала новую задачу"),
        UpdatesTest("Алиса выполнила задачу"),
        UpdatesTest("Борис выбрал награду"),
        UpdatesTest("Борис выполнил задачу"),
        UpdatesTest("Борис выполнил задачу"),
        UpdatesTest("Алиса создала новую задачу"),
        UpdatesTest("Алиса выполнила задачу"),
        UpdatesTest("Борис выбрал награду"),
        UpdatesTest("Борис выполнил задачу"),
        UpdatesTest("Борис выполнил задачу")
    )
}