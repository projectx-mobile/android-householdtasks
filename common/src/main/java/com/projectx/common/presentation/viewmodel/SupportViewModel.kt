package com.projectx.common.presentation.viewmodel

import com.projectx.common.presentation.fragment.SupportFragmentDirections
import com.projectx.common.presentation.model.SettingModel
import com.projectx.common.presentation.model.supportSettingsList
import com.projectx.common.presentation.navigation.NavEvent

class SupportViewModel : BaseViewModel() {

    fun getSettingList(): List<SettingModel> {
        return supportSettingsList
    }

    fun onToolbarClick() {
        navigate(NavEvent.Up)
    }

    fun handleClick(item: SettingModel) {
        when (item) { //todo navigation
            SettingModel.FAQ -> {
                navigate(NavEvent.To(SupportFragmentDirections.actionSupportScreenFragmentToBottomSheetFaqFragment()))
            }
            SettingModel.PRIVACY_POLICY -> {}
            SettingModel.FEEDBACK -> {}
            else -> {}
        }
    }
}