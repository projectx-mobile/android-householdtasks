package com.projectx.householdtasks.presentation.viewmodel

import androidx.lifecycle.LiveData
import com.projectx.householdtasks.R
import com.projectx.householdtasks.domain.model.FamilyMemberTest
import com.projectx.householdtasks.domain.model.UpdatesTest
import com.projectx.householdtasks.presentation.FamilyMember
import com.projectx.householdtasks.presentation.event.ParentHomeScreenEvent
import com.projectx.householdtasks.presentation.fragment.ParentHomescreenFragmentDirections
import com.projectx.householdtasks.presentation.state.UiState

class ParentHomescreenViewModel : BaseViewModel<ParentHomescreenViewModel.ParentHomeScreenUiState, ParentHomeScreenEvent>() {

    override val state =
        object : LiveData<UiState<ParentHomeScreenUiState>>(UiState.Ready(ParentHomeScreenUiState(createFamilyTestsList(), getUpdates()))) {}

    override fun onEvent(event: ParentHomeScreenEvent) {
        when (event) {
            is ParentHomeScreenEvent.NavigateToAllUpdates -> event.navController.navigate(
                ParentHomescreenFragmentDirections.actionParentHomescreenFragmentToAllUpdatesFragment()
            )
            else -> super.onEvent(event)
        }
    }

    fun createFamilyTestsList(): List<FamilyMemberTest> {
        return listOf(
            FamilyMemberTest("Алиса", 0, 0),
            FamilyMemberTest("Борис", 0, 0),
            FamilyMemberTest("Алиса", 0, 0),
            FamilyMemberTest("Борис", 0, 0),
            FamilyMemberTest("Алиса", 0, 0),
            FamilyMemberTest("Борис", 0, 0),
            FamilyMemberTest("Алиса", 0, 0),
            FamilyMemberTest("Борис", 0, 0),
            FamilyMemberTest("Приглашен", 0, 0),
        )
    }

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

    data class ParentHomeScreenUiState(
        val familyMemberTests: List<FamilyMemberTest>,
        val updates: List<UpdatesTest>
    )
}