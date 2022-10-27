package com.projectx.parent.presentation.viewmodel

import com.projectx.common.presentation.viewmodel.BaseViewModel
import com.projectx.householdtasks.parent.domain.model.FamilyMemberTest
import com.projectx.householdtasks.parent.domain.model.UpdatesTest
//import com.projectx.parent.presentation.fragment.ParentHomescreenFragmentDirections

class ParentHomescreenViewModel : BaseViewModel() {

    fun navigateToAllUpdates() {}//= navigate(NavEvent.To(ParentHomescreenFragmentDirections.actionParentHomescreenFragmentToAllUpdatesFragment()))

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