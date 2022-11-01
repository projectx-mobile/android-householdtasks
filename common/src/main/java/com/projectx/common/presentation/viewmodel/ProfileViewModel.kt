package com.projectx.common.presentation.viewmodel

import com.projectx.common.domain.use_case.GetFamilyMembersUseCase
import com.projectx.common.presentation.fragment.ProfileFragmentDirections
import com.projectx.common.presentation.model.FamilyMember
import com.projectx.common.presentation.model.SettingModel
import com.projectx.common.presentation.model.profileOtherSettingList
import com.projectx.common.presentation.navigation.NavEvent
import com.projectx.common.presentation.state.UiState

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

    fun onNavigationBarClick() =
        navigate(NavEvent.To(ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment()))

    fun onPersonPhotoClick() =
        navigate(NavEvent.To(ProfileFragmentDirections.actionProfileFragmentToInviteUserFragment()))

    fun handleClick(item: SettingModel) { //todo navigation
        val destination = when (item) {
            SettingModel.EMAIL -> ProfileFragmentDirections.actionProfileFragmentToEditProfileEmailFragment()
            SettingModel.PASSWORD -> ProfileFragmentDirections.actionProfileFragmentToEditProfilePasswordFragment()
            SettingModel.PARENT -> ProfileFragmentDirections.actionProfileFragmentToAccountStatusFragment()
            SettingModel.LINKED_ACCOUNTS -> null
            SettingModel.NOTIFICATIONS -> ProfileFragmentDirections.actionProfileFragmentToNotificationFragment()
            SettingModel.LANGUAGE -> ProfileFragmentDirections.actionProfileFragmentToDialogChangeLanguageFragment()
            SettingModel.SUPPORT -> ProfileFragmentDirections.actionProfileFragmentToSupportScreenFragment()
            else -> null
        }
        destination?.let {
            navigate(NavEvent.To(it))
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