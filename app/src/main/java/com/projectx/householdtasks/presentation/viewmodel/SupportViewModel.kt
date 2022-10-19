package com.projectx.householdtasks.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.projectx.householdtasks.R
import com.projectx.householdtasks.presentation.adapter.SettingModel
import com.projectx.householdtasks.presentation.adapter.supportSettingsList
import com.projectx.householdtasks.presentation.event.SupportScreenEvent
import com.projectx.householdtasks.presentation.state.UiState

class SupportViewModel : BaseViewModel<List<SettingModel>, SupportScreenEvent>() {

    override val state = object : LiveData<UiState<List<SettingModel>>>(
        UiState.Ready(getSettingList())
    ) {}

    override fun onEvent(event: SupportScreenEvent) {
        when (event) {
            is SupportScreenEvent.OnItemClicked -> handleClick(event.item, event.navController)
            else -> super.onEvent(event)
        }
    }

    private fun getSettingList(): List<SettingModel> {
        return supportSettingsList
    }

    private fun handleClick(item: SettingModel, navController: NavController) {
        when (item) {
            SettingModel.FAQ -> {
                navController.navigate(R.id.action_supportScreenFragment_to_bottomSheetFaqFragment)
            } //todo navigation
            SettingModel.PRIVACY_POLICY -> {}
            SettingModel.FEEDBACK -> {}
            else -> {}
        }
    }
}