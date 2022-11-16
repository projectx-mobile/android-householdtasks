package com.projectx.householdtasks.presentation.viewmodel

import androidx.navigation.NavController
import com.projectx.householdtasks.R
import com.projectx.householdtasks.presentation.adapter.SettingModel
import com.projectx.householdtasks.presentation.adapter.supportSettingsList

class SupportViewModel : BaseViewModel() {

    fun getSettingList(): List<SettingModel> {
        return supportSettingsList
    }

    fun handleClick(item: SettingModel, navController: NavController) {
        when (item) {

            SettingModel.FAQ -> {
                navController.navigate(R.id.action_supportScreenFragment_to_bottomSheetFaqFragment)
            } //todo navigation
            SettingModel.PRIVACY_POLICY -> {}
            SettingModel.FEEDBACK -> {}
        }
    }
}