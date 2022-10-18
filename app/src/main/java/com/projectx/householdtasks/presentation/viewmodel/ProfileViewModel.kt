package com.projectx.householdtasks.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.projectx.householdtasks.R
import com.projectx.householdtasks.presentation.FamilyMember
import com.projectx.householdtasks.presentation.adapter.SettingModel
import com.projectx.householdtasks.presentation.adapter.profileOtherSettingList
import com.projectx.householdtasks.presentation.event.ProfileScreenEvent
import com.projectx.householdtasks.presentation.state.UiState

class ProfileViewModel : BaseViewModel<ProfileViewModel.ProfileViewUiState, ProfileScreenEvent>() {

    override val state = object :
        LiveData<UiState<ProfileViewUiState>>(UiState.Ready(ProfileViewUiState(getFamilyMembers()))) {}

    override fun onEvent(event: ProfileScreenEvent) {
        when (event) {
            is ProfileScreenEvent.OnItemClicked -> handleClick(event.item, event.navController)
            is ProfileScreenEvent.NavigateToEditProfile -> event.navController.navigate(R.id.action_profileFragment_to_editProfileFragment)
        }
        super.onEvent(event)
    }

    private fun handleClick(item: SettingModel, navController: NavController) {
        when (item) {
            SettingModel.EMAIL -> {
                navController.navigate(R.id.action_profileFragment_to_editProfileEmailFragment)
            } //todo navigation
            SettingModel.PASSWORD -> {
                navController.navigate(R.id.action_profileFragment_to_editProfilePasswordFragment)
            }
            SettingModel.PARENT -> {
                navController.navigate(R.id.action_profileFragment_to_accountStatusFragment)
            }
            SettingModel.LINKED_ACCOUNTS -> {}
            SettingModel.NOTIFICATIONS -> {
                navController.navigate(R.id.action_profileFragment_to_notificationFragment)
            }
            SettingModel.LANGUAGE -> {
                navController.navigate(R.id.action_profileFragment_to_dialogChangeLanguageFragment)
            }
            SettingModel.SUPPORT -> {
                navController.navigate(R.id.action_profileFragment_to_supportScreenFragment)
            }
            else -> {}
        }
    }

    private fun getFamilyMembers(): List<FamilyMember> {
        return listOf(
            FamilyMember("Добавить", R.drawable.selector_add_button),
            FamilyMember("Алиса", null),
            FamilyMember("Борис", R.drawable.ic_avata),
            FamilyMember("Алиса", null),
            FamilyMember("Борис", null),
            FamilyMember("Алиса", null),
            FamilyMember("Борис", null),
            FamilyMember("Алиса", null),
            FamilyMember("Борис", null),
            FamilyMember("Приглашен", R.drawable.button_invited_person),
        )
    }

    data class ProfileViewUiState(
        val familyMembers: List<FamilyMember>,
        val settingList: List<SettingModel> = profileOtherSettingList,
        val otherSettingList: List<SettingModel> = profileOtherSettingList
    )
}