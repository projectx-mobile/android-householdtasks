package com.projectx.auth.presentation.fragment

import com.projectx.auth.databinding.FragmentSignUpEmailConfirmationBinding
import com.projectx.auth.presentation.viewmodel.SignUpConfirmationViewModel
import com.projectx.common.presentation.fragment.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpEmailConfirmationFragment:
    BaseFragment<FragmentSignUpEmailConfirmationBinding, SignUpConfirmationViewModel>(FragmentSignUpEmailConfirmationBinding::inflate) {

    override val viewModel by viewModel<SignUpConfirmationViewModel>()

    override fun FragmentSignUpEmailConfirmationBinding.bindUI() {
//            viewModel.createAccount()
            viewModel.createAccountWithDelay()
    }

    override fun SignUpConfirmationViewModel.subscribeUI() {

    }
}