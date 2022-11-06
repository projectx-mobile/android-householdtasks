package com.projectx.auth.presentation.viewmodel

import com.projectx.auth.presentation.fragment.CreateAccountNotificationsFragmentDirections
import com.projectx.common.presentation.navigation.NavEvent
import com.projectx.common.presentation.viewmodel.BaseViewModel

class CreateAccountNotificationsViewModel: BaseViewModel() {

    fun goToFinishFragment() {
        navigate(NavEvent.To(CreateAccountNotificationsFragmentDirections.actionCreateAccountNotificationsFragmentToCreateAccountFinishFragment()))
    }
}