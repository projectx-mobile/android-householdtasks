package com.projectx.auth.presentation.viewmodel

import android.os.Handler
import android.os.Looper
import com.projectx.auth.presentation.fragment.SignUpEmailConfirmationFragmentDirections
import com.projectx.common.presentation.navigation.NavEvent
import com.projectx.common.presentation.viewmodel.BaseViewModel

class SignUpConfirmationViewModel : BaseViewModel() {

    fun createAccountWithDelay() {
        Handler(Looper.getMainLooper()).postDelayed({
            createAccount()
        }, 3000)
    }

    fun createAccount() {
        navigate(NavEvent.To(SignUpEmailConfirmationFragmentDirections.actionSignUpEmailConfirmationFragmentToCreateNewAccountFragment()))
    }
}