package com.projectx.householdtasks.parent.presentation.viewmodel

import com.projectx.common.domain.use_case.GetFamilyMemberTestListUseCase
import com.projectx.common.domain.use_case.GetUpdatesTestListUseCase
import com.projectx.common.presentation.navigation.NavEvent
import com.projectx.common.presentation.viewmodel.BaseViewModel
import com.projectx.common.presentation.model.FamilyMemberTest
import com.projectx.common.presentation.model.UpdatesTest
import com.projectx.householdtasks.parent.presentation.fragment.ParentHomescreenFragmentDirections

class ParentHomescreenViewModel(
    private val getFamilyMemberTestListUseCase: GetFamilyMemberTestListUseCase,
    private val getUpdatesTestListUseCase: GetUpdatesTestListUseCase
) : BaseViewModel() {

    init {
        getFamilyMembers()
        getUpdates()
    }

    val familyMemberTestList = MutableUiState<List<FamilyMemberTest>>()

    val updatesTestList = MutableUiState<List<UpdatesTest>>()

    fun navigateToAllUpdates() = navigate(NavEvent.To(ParentHomescreenFragmentDirections.actionParentHomescreenFragmentToAllUpdatesFragment()))

    fun getFamilyMembers() {
        useCaseHandler(getFamilyMemberTestListUseCase, Unit, familyMemberTestList)
    }

    fun getUpdates() {
        useCaseHandler(getUpdatesTestListUseCase, Unit, updatesTestList)
    }
}