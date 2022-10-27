package com.projectx.auth.presentation.viewmodel

import androidx.appcompat.app.AppCompatActivity
import com.projectx.auth.data.authentication.Authentication
import com.projectx.common.presentation.viewmodel.BaseViewModel
import com.projectx.auth.presentation.fragment.ChooseLoginTypeFragmentDirections
import com.projectx.common.presentation.navigation.NavEvent

class ChooseLoginTypeViewModel : BaseViewModel() {

    fun loginWithGoogle(activity: AppCompatActivity) {
        Authentication.signInWithGoogle(activity)
    }

    fun loginWithEmail() {
        navigate(NavEvent.To(ChooseLoginTypeFragmentDirections.actionChooseLoginTypeFragmentToLoginFragment()))
    }

    fun createAccount() {

    }
}