package com.projectx.auth.presentation.viewmodel

import com.projectx.auth.presentation.fragment.SignUpEmailConfirmationFragmentDirections
import com.projectx.common.presentation.navigation.NavEvent
import com.projectx.common.presentation.viewmodel.BaseViewModel

class SignUpConfirmationViewModel: BaseViewModel() {

    fun createAccount() {
        navigate(NavEvent.To(SignUpEmailConfirmationFragmentDirections.actionSignUpEmailConfirmationFragmentToCreateNewAccountFragment()))
    }
}