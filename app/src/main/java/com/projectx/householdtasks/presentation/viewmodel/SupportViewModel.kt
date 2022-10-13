package com.projectx.householdtasks.presentation.viewmodel

import com.projectx.householdtasks.presentation.adapter.SettingModel
import com.projectx.householdtasks.presentation.adapter.supportSettingsList

class SupportViewModel : BaseViewModel() {

    fun getSettingList(): List<SettingModel> {
        return supportSettingsList
    }

    fun handleClick(item: SettingModel) {
        when (item) {
            SettingModel.FAQ -> {} //todo navigation
            SettingModel.PRIVACY_POLICY -> {}
            SettingModel.FEEDBACK -> {}
        }
    }
}