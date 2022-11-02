package com.projectx.common.presentation.viewmodel

import com.projectx.common.domain.use_case.GetFamilyMembersUseCase
import com.projectx.common.presentation.model.FamilyMember
import com.projectx.common.presentation.navigation.NavEvent

class AccountStatusViewModel(
    private val getFamilyMembersUseCase: GetFamilyMembersUseCase
) : BaseViewModel() {

    val familyMembers = MutableUiState<List<FamilyMember>>()

    init {
        getFamilyMembers()
    }

    fun getFamilyMembers() {
        useCaseHandler(getFamilyMembersUseCase, Unit, familyMembers)
    }

    fun onToolbarArrowClick() {
        navigate(NavEvent.Up)
    }
}