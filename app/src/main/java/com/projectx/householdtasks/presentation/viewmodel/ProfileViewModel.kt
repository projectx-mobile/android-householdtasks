package com.projectx.householdtasks.presentation.viewmodel

import androidx.navigation.NavController
import com.projectx.householdtasks.R
import com.projectx.householdtasks.presentation.FamilyMember
import com.projectx.householdtasks.presentation.adapter.SettingModel
import com.projectx.householdtasks.presentation.adapter.profileOtherSettingList
import com.projectx.householdtasks.presentation.adapter.profileSettingList

class ProfileViewModel : BaseViewModel() {
    fun getSettingList(): List<SettingModel> {
        return profileSettingList
    }

    fun getOtherSettingList(): List<SettingModel> {
        return profileOtherSettingList
    }

    fun getFamilyMembers(): List<FamilyMember> {
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

    fun handleClick(item: SettingModel, navController: NavController) {
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
        }
    }
}