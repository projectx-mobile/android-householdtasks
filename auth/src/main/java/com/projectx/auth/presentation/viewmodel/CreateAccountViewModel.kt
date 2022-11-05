package com.projectx.auth.presentation.viewmodel

import com.projectx.auth.presentation.fragment.CreateAccountFragmentDirections
import com.projectx.common.presentation.navigation.NavEvent
import com.projectx.common.presentation.viewmodel.BaseViewModel

class CreateAccountViewModel: BaseViewModel() {

    fun goToNotificationPermissionScreen() {
        navigate(NavEvent.To(CreateAccountFragmentDirections.actionCreateAccountFragmentToCreateAccountNotificationsFragment()))
    }
    fun backToOnBoardingFragment() {
        navigate(NavEvent.To(CreateAccountFragmentDirections.actionCreateAccountFragmentToOnBoardingFragment()))
    }
}