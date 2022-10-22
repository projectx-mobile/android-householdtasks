package com.projectx.householdtasks.presentation.viewmodel

import com.projectx.householdtasks.domain.model.FamilyMemberTest
import com.projectx.householdtasks.domain.model.UpdatesTest
import com.projectx.householdtasks.presentation.fragment.ParentHomescreenFragmentDirections
import com.projectx.householdtasks.presentation.navigation.NavEvent

class ParentHomescreenViewModel : BaseViewModel() {

    fun navigateToAllUpdates() =
        navigate(NavEvent.To(ParentHomescreenFragmentDirections.actionParentHomescreenFragmentToAllUpdatesFragment()))

    private val familyMembers = listOf(
        FamilyMemberTest("John", 5, 1),
        FamilyMemberTest("Алиса", 3, 2),
    )

    fun getFamilyMembers(): List<FamilyMemberTest>? {
        return null
        return familyMembers
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

    fun getUpdates(): List<UpdatesTest>? {

        return updates
        return null
    }
}