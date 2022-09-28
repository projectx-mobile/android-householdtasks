package com.projectx.householdtasks.presentation.viewmodel

import androidx.navigation.NavController
import com.projectx.householdtasks.R

class ChooseLoginTypeViewModel : BaseViewModel() {

    fun loginWithGoogle(){}

    fun loginWithEmail(navController: NavController){
        navController.navigate(R.id.action_chooseLoginTypeFragment_to_loginFragment)
    }

    fun createAccount(){}
}