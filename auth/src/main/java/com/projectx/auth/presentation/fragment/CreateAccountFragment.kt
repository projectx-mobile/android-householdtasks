package com.projectx.auth.presentation.fragment

import com.projectx.auth.databinding.FragmentCreateAccountBinding
import com.projectx.auth.presentation.viewmodel.CreateAccountViewModel
import com.projectx.common.presentation.fragment.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateAccountFragment :
    BaseFragment<FragmentCreateAccountBinding, CreateAccountViewModel>(
        FragmentCreateAccountBinding::inflate
    ) {
    override val viewModel by viewModel<CreateAccountViewModel>()

    override fun FragmentCreateAccountBinding.bindUI() {
        appbarCreateAccount.signUpToolbar.setNavigationOnClickListener {
            viewModel.backToOnBoardingFragment()
        }
        buttonFinishRegistration.setOnClickListener {
            viewModel.goToNotificationPermissionScreen()
        }
    }

    override fun CreateAccountViewModel.subscribeUI() {

    }
}