package com.projectx.householdtasks.presentation.viewmodel

import androidx.lifecycle.LiveData
import com.projectx.householdtasks.domain.model.FamilyMemberTest
import com.projectx.householdtasks.domain.model.UpdatesTest
import com.projectx.householdtasks.presentation.event.NavEvent
import com.projectx.householdtasks.presentation.state.UiState

class ParentHomescreenViewModel : BaseViewModel<List<UpdatesTest>, NavEvent>() {

    override val state = object : LiveData<UiState<List<UpdatesTest>>>(UiState.Ready(getUpdates())) {}

    fun getUpdates(): List<UpdatesTest> {
        return updates
    }

    private val updates = listOf(
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