package com.projectx.auth.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.projectx.auth.presentation.fragment.CreateAccountFragmentDirections
import com.projectx.common.domain.use_case.ValidatePasswordUseCase
import com.projectx.common.presentation.navigation.NavEvent
import com.projectx.common.presentation.viewmodel.BaseViewModel

class CreateAccountViewModel(
    private val validatePasswordUseCase: ValidatePasswordUseCase
): BaseViewModel() {

    private val _name = MutableLiveData("")
    val name: LiveData<String> = Transformations.distinctUntilChanged(_name)

    private val _password = MutableLiveData("")
    val password: LiveData<String> = Transformations.distinctUntilChanged(_password)

    private val _passwordConfirm = MutableLiveData("")
    val passwordConfirm: LiveData<String> = Transformations.distinctUntilChanged(_passwordConfirm)



    fun goToNotificationPermissionScreen() {
        navigate(NavEvent.To(CreateAccountFragmentDirections.actionCreateAccountFragmentToCreateAccountNotificationsFragment()))
    }
    fun backToOnBoardingFragment() {
        navigate(NavEvent.To(CreateAccountFragmentDirections.actionCreateAccountFragmentToOnBoardingFragment()))
    }
}