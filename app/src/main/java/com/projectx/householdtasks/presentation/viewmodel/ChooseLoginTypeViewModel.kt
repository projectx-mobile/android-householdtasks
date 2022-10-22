package com.projectx.householdtasks.presentation.viewmodel

import androidx.appcompat.app.AppCompatActivity
import com.projectx.auth.Authentication
import com.projectx.householdtasks.presentation.fragment.ChooseLoginTypeFragmentDirections
import com.projectx.householdtasks.presentation.navigation.NavEvent

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