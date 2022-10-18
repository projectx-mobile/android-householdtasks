package com.projectx.householdtasks.presentation.viewmodel

import androidx.navigation.NavController
import com.projectx.householdtasks.R
import com.projectx.householdtasks.presentation.event.ChooseLoginTypeScreenEvent

class ChooseLoginTypeViewModel : BaseViewModel<Nothing, ChooseLoginTypeScreenEvent>() {

    override fun onEvent(event: ChooseLoginTypeScreenEvent) {
        when (event) {
            is ChooseLoginTypeScreenEvent.LoginWithGoogle -> loginWithGoogle()
            is ChooseLoginTypeScreenEvent.LoginWithEmail -> loginWithEmail(event.navController)
            is ChooseLoginTypeScreenEvent.CreateAccount -> createAccount()
        }
    }

    private fun loginWithGoogle() {}

    private fun loginWithEmail(navController: NavController) {
        navController.navigate(R.id.action_chooseLoginTypeFragment_to_loginFragment)
    }

    private fun createAccount() {}
}