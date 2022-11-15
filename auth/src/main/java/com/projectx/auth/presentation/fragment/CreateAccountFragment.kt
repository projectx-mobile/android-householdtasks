package com.projectx.auth.presentation.fragment

import androidx.core.widget.addTextChangedListener
import com.projectx.auth.databinding.FragmentCreateAccountBinding
import com.projectx.auth.presentation.viewmodel.CreateAccountViewModel
import com.projectx.common.R
import com.projectx.common.presentation.fragment.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateAccountFragment :
    BaseFragment<FragmentCreateAccountBinding, CreateAccountViewModel>(
        FragmentCreateAccountBinding::inflate
    ) {
    override val viewModel by viewModel<CreateAccountViewModel>()

    override fun FragmentCreateAccountBinding.bindUI() {
        name.addTextChangedListener {
            viewModel.onNameTextChanged(it.toString())
        }
        password.addTextChangedListener {
            viewModel.onPasswordTextChanged(it.toString())
        }
        passwordConfirm.addTextChangedListener {
            viewModel.onPasswordConfirmTextChanged(it.toString())
        }
        appbarCreateAccount.signUpToolbar.setNavigationOnClickListener {
            viewModel.backToOnBoardingFragment()
        }
        buttonFinishRegistration.setOnClickListener {
            viewModel.onFinishRegistrationButtonClick()
            viewModel.goToNotificationPermission()
        }
    }

    override fun CreateAccountViewModel.subscribeUI() {
        binding.apply {
            state.observeUiState {
                resetError()
                addNamePasswordObserver(
                    it.name.value,
                    it.password.value,
                    it.passwordConfirm.value
                )
                addValidationResultObserver(
                    it.nameValidationResult.value,
                    it.passwordValidationResult.value
                )
                addRequestResultObserver(
                    it.requestResult.value
                )
            }
        }
    }

    private fun FragmentCreateAccountBinding.addNamePasswordObserver(
        newName: String?,
        password: String?,
        passwordConfirm: String?
    ) {
        if (newAccountName.editText?.text.toString() != newName) {
            newAccountName.editText?.setText(newName)
        }
        if (newAccountPassword.editText?.text.toString() != password) {
            newAccountPassword.editText?.setText(password)
        }
        if (newAccountPasswordConfirm.editText?.text.toString() != passwordConfirm) {
            newAccountPasswordConfirm.editText?.setText(passwordConfirm)
        }
        buttonFinishRegistration.isEnabled = viewModel.isFinishRegistrationButtonEnabled()
    }

    private fun FragmentCreateAccountBinding.addValidationResultObserver(
        nameValidationResult: CreateAccountViewModel.Companion.NameValidationResult?,
        passwordValidationResult: CreateAccountViewModel.Companion.PasswordValidationResult?,
    ) {
        when (nameValidationResult) {
            CreateAccountViewModel.Companion.NameValidationResult.LENGTH_ERROR -> setErrorForNameLength()
            CreateAccountViewModel.Companion.NameValidationResult.NO_LETTERS_ERROR -> setErrorForNameNotContainsLetters()
            CreateAccountViewModel.Companion.NameValidationResult.INVALID_CHARACTER_ERROR -> setErrorForNameContainsOtherSymbols()
            CreateAccountViewModel.Companion.NameValidationResult.OK -> {}
            else -> {}
        }
        when (passwordValidationResult) {
            CreateAccountViewModel.Companion.PasswordValidationResult.LENGTH_ERROR -> setErrorForPasswordLength()
            CreateAccountViewModel.Companion.PasswordValidationResult.FORMAT_ERROR -> setErrorForPasswordFormat()
            CreateAccountViewModel.Companion.PasswordValidationResult.CONFIRM_ERROR -> setErrorForPasswordConfirm()
            CreateAccountViewModel.Companion.PasswordValidationResult.OK -> {}
            else -> {}
        }
    }

    private fun FragmentCreateAccountBinding.addRequestResultObserver(requestResult: CreateAccountViewModel.Companion.RequestResult?) {
        when (requestResult) {
            CreateAccountViewModel.Companion.RequestResult.SUCCESS -> viewModel.onRequestSuccess()
            CreateAccountViewModel.Companion.RequestResult.ERROR -> setConnectionError()
            else -> {}
        }
    }

    private fun FragmentCreateAccountBinding.resetError() {
        newAccountName.error = ""
        newAccountPassword.error = ""
        newAccountPasswordConfirm.error = ""
    }


    private fun FragmentCreateAccountBinding.setErrorForNameLength() {
        newAccountName.error = getString(R.string.name_length_error)
    }

    private fun FragmentCreateAccountBinding.setErrorForNameNotContainsLetters() {
        newAccountName.error = getString(R.string.name_not_contains_letter)
    }
    private fun FragmentCreateAccountBinding.setErrorForNameContainsOtherSymbols() {
        newAccountName.error = getString(R.string.name_not_contains_letter)
    }

    private fun FragmentCreateAccountBinding.setErrorForPasswordLength() {
        newAccountName.error = getString(R.string.password_length_error)
    }

    private fun FragmentCreateAccountBinding.setErrorForPasswordFormat() {
        newAccountName.error = getString(R.string.password_format_error)
    }

    private fun FragmentCreateAccountBinding.setErrorForPasswordConfirm() {
        newAccountName.error = getString(R.string.password_confirm_error)
    }

    //TODO: add error for failure request
    private fun FragmentCreateAccountBinding.setConnectionError() {
        newAccountName.error = getString(R.string.connection_error)
    }
}
