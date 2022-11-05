package com.projectx.auth.presentation.fragment

import com.projectx.auth.R
import com.projectx.auth.databinding.FragmentSignUpWithEmailBinding
import com.projectx.auth.presentation.viewmodel.SignUpWithEmailViewModel
import com.projectx.common.presentation.fragment.BaseFragment
import com.projectx.common.presentation.navigation.NavEvent
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpWithEmailFragment :
    BaseFragment<FragmentSignUpWithEmailBinding, SignUpWithEmailViewModel> (FragmentSignUpWithEmailBinding::inflate) {

    override val viewModel by viewModel<SignUpWithEmailViewModel>()

    override fun FragmentSignUpWithEmailBinding.bindUI() {
        appbarSignUp.signUpToolbar.setNavigationOnClickListener {
            viewModel.navigate(NavEvent.Up)
        }
        buttonSignUpSubmitEmail.setOnClickListener {
            viewModel.checkEmail()
        }

        buttonSignUpSubmitEmail.isEnabled = true
        hideEmailErrorsOnChange()
    }

    override fun SignUpWithEmailViewModel.subscribeUI() {
        email.observe(viewLifecycleOwner) {
            hideEmailErrorsOnChange()
        }
    }

    private fun hideEmailErrorsOnChange() {
        binding.emailLogin.isErrorEnabled = false
    }

    private fun setErrorForEmail() {
        binding.emailLogin.isErrorEnabled = true
        binding.emailLogin.error = getString(R.string.email_error)
    }


}