package com.projectx.common.presentation.fragment

import android.view.View
import androidx.core.widget.addTextChangedListener
import com.projectx.common.R
import com.projectx.common.databinding.FragmentEditProfileEmailBinding
import com.projectx.common.domain.use_case.ValidateEmailUseCase
import com.projectx.common.presentation.viewmodel.EditProfileEmailViewModel
import com.projectx.common.presentation.viewmodel.EditProfileViewModel.Companion.RequestResult
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditProfileEmailFragment :
    BaseFragment<FragmentEditProfileEmailBinding, EditProfileEmailViewModel>(
        FragmentEditProfileEmailBinding::inflate
    ) {

    override val viewModel by viewModel<EditProfileEmailViewModel>()

    override fun FragmentEditProfileEmailBinding.bindUI() {
        //TODO: set current email
        currentEmailLayout.editText?.setText("name@example.com")

        addEmailChangedListener()

        toolbarLayout.toolbarArrowBack.setOnClickListener {
            viewModel.onToolbarArrowClick()
        }
        buttonSaveChanges.setOnClickListener {
            viewModel.handleSaveChanges()
        }
    }

    override fun EditProfileEmailViewModel.subscribeUI() {
        binding.apply {
            addEmailObserver()
            addUiStateObserver()
            isContinueButtonEnabled.observe(viewLifecycleOwner) {
                buttonSaveChanges.isEnabled = it
            }
        }
    }

    private fun FragmentEditProfileEmailBinding.addEmailChangedListener() {
        newEmail.addTextChangedListener {
            viewModel.setNewEmailValue(it.toString())
            buttonSaveChanges.isEnabled = viewModel.isSaveButtonEnabled.value ?: false
            viewModel.resetEmailError()
        }
    }

    private fun FragmentEditProfileEmailBinding.addEmailObserver() {
        viewModel.newEmail.observe(viewLifecycleOwner) {
            if (newEmailLayout.editText?.text.toString() != it) {
                newEmailLayout.editText?.setText(it)
            }
            buttonSaveChanges.isEnabled = viewModel.isSaveButtonEnabled.value ?: false
        }
    }

    private fun FragmentEditProfileEmailBinding.addUiStateObserver() {
        viewModel.uiState.observe(viewLifecycleOwner) {
            resetError()
            helpMessage.visibility = View.INVISIBLE

            when (it.emailValidationResult) {
                ValidateEmailUseCase.EmailValidationResult.ERROR -> setErrorForEmail()
                ValidateEmailUseCase.EmailValidationResult.OK -> {}
                else -> {}
            }

            when (it.requestResult) {
                RequestResult.Success -> {
                    helpMessage.visibility = View.VISIBLE
                    viewModel.onRequestSuccess()
                }
                RequestResult.RequestFailedError -> setConnectionError()
                else -> {}
            }
        }
    }

    private fun resetError() {
        binding.newEmailLayout.error = ""
    }

    private fun setErrorForEmail() {
        binding.newEmailLayout.error = getString(R.string.email_error)
    }

    //    TODO: add error for failure request
    private fun setConnectionError() {
        binding.newEmailLayout.error = "Ошибка подключения к серверу"
    }
}
