package com.projectx.householdtasks.presentation.viewmodel

import androidx.navigation.NavController
import com.projectx.householdtasks.R
import com.projectx.householdtasks.domain.use_case.GetFamilyMembersUseCase
import com.projectx.householdtasks.presentation.FamilyMember
import com.projectx.householdtasks.presentation.adapter.SettingModel
import com.projectx.householdtasks.presentation.adapter.profileOtherSettingList
import com.projectx.householdtasks.presentation.state.UiState

class ProfileViewModel(
    private val getFamilyMembersUseCase: GetFamilyMembersUseCase
) : BaseViewModel() {

    val familyMembers = MutableUiState<List<FamilyMember>>()

    val settingList = MutableUiState<List<SettingModel>>()

    val otherSettingList = MutableUiState<List<SettingModel>>()

    init {
        getFamilyMembers()
        getSettingList()
        getOtherSettingList()
    }

    fun navigateToEditProfile(navController: NavController) =
        navigate(navController, R.id.action_profileFragment_to_editProfileFragment)

    fun handleClick(item: SettingModel, navController: NavController) {
        val destination = when (item) {
            SettingModel.EMAIL -> R.id.action_profileFragment_to_editProfileEmailFragment //todo navigation
            SettingModel.PASSWORD -> R.id.action_profileFragment_to_editProfilePasswordFragment
            SettingModel.PARENT -> R.id.action_profileFragment_to_accountStatusFragment
            SettingModel.LINKED_ACCOUNTS -> null
            SettingModel.NOTIFICATIONS -> R.id.action_profileFragment_to_notificationFragment
            SettingModel.LANGUAGE -> R.id.action_profileFragment_to_dialogChangeLanguageFragment
            SettingModel.SUPPORT -> R.id.action_profileFragment_to_supportScreenFragment
            else -> null
        }
        destination?.let {
            navigate(navController, it)
        }
    }

    private fun getOtherSettingList() {
        otherSettingList.postValue(UiState.Ready(profileOtherSettingList))
    }

    private fun getSettingList() {
        settingList.postValue(UiState.Ready(profileOtherSettingList))
    }

    private fun getFamilyMembers() {
        /**
         * Example 1
         */
        useCaseHandler(getFamilyMembersUseCase, Unit, familyMembers)
        /**
         * Example 2
        getFamilyMembersUseCase.execute(Unit)
        .onStart { familyMembers.postValue(UiState.Loading) }
        .onEach { familyMembers.postValue(UiState.Ready(it)) }
        .catch { familyMembers.postValue(UiState.Error(error = it)) }
        .launchIn(viewModelScope)
         */

        /**
         * Example 3
        useCaseHandler(getFamilyMembersUseCase, Unit, result = {
        familyMembers.postValue(it.toUiState())
        })
         */
    }
}