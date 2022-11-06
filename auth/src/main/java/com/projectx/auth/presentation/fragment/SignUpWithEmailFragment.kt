package com.projectx.auth.presentation.fragment

import android.view.View
import androidx.core.widget.addTextChangedListener
import com.projectx.auth.databinding.FragmentSignUpWithEmailBinding
import com.projectx.auth.presentation.viewmodel.SignUpWithEmailViewModel
import com.projectx.common.R
import com.projectx.common.databinding.FragmentEditProfileEmailBinding
import com.projectx.common.domain.use_case.ValidateEmailUseCase
import com.projectx.common.presentation.fragment.BaseFragment
import com.projectx.common.presentation.viewmodel.EditProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpWithEmailFragment :
    BaseFragment<FragmentSignUpWithEmailBinding, SignUpWithEmailViewModel> (FragmentSignUpWithEmailBinding::inflate) {

    override val viewModel by viewModel<SignUpWithEmailViewModel>()

    override fun FragmentSignUpWithEmailBinding.bindUI() {
        addEmailChangedListener()
        appbarSignUp.signUpToolbar.setNavigationOnClickListener {
            viewModel.onToolbarArrowClick()
        }
        buttonSubmitEmail.setOnClickListener {
            viewModel.handleSaveChanges()
        }
    }

    override fun SignUpWithEmailViewModel.subscribeUI() {
        binding.apply {
            addEmailObserver()
            addUiStateObserver()
            isContinueButtonEnabled.observe(viewLifecycleOwner) {
                buttonSubmitEmail.isEnabled = it
            }
        }
    }

    private fun FragmentSignUpWithEmailBinding.addEmailChangedListener() {
        email.addTextChangedListener {
            viewModel.setEmailValue(it.toString())
            buttonSubmitEmail.isEnabled = viewModel.isContinueButtonEnabled.value ?: false
            viewModel.resetEmailError()
        }
    }

    private fun FragmentSignUpWithEmailBinding.addEmailObserver() {
        viewModel.email.observe(viewLifecycleOwner) {
            if (emailSignUpLayout.editText?.text.toString() != it) {
                emailSignUpLayout.editText?.setText(it)
            }
            buttonSubmitEmail.isEnabled = viewModel.isContinueButtonEnabled.value ?: false
        }
    }

    private fun FragmentSignUpWithEmailBinding.addUiStateObserver() {
        viewModel.uiState.observe(viewLifecycleOwner) {
            resetError()
//           helpMessage.visibility = View.INVISIBLE

            when (it.emailValidationResult) {
                ValidateEmailUseCase.EmailValidationResult.ERROR -> setErrorForEmail()
                ValidateEmailUseCase.EmailValidationResult.OK -> {}
                else -> {}
            }

            when (it.requestResult) {
                SignUpWithEmailViewModel.RequestResult.SUCCESS -> {
//                    helpMessage.visibility = View.VISIBLE
                    viewModel.onRequestSuccess()
                }
                SignUpWithEmailViewModel.RequestResult.REQUESTFAILEDERROR -> setConnectionError()
                else -> {}
            }
        }
    }

    private fun resetError() {
        binding.emailSignUpLayout.error = ""
    }

    private fun setErrorForEmail() {
        binding.emailSignUpLayout.error = getString(R.string.email_error)
    }

    //    TODO: add error for failure request
    private fun setConnectionError() {
        binding.emailSignUpLayout.error = "Ошибка подключения к серверу"
    }
}