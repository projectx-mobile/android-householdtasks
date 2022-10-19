package com.projectx.householdtasks.presentation.fragment

import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.projectx.householdtasks.R
import com.projectx.householdtasks.databinding.FragmentInviteUserByEmailBinding
import com.projectx.householdtasks.presentation.EmailValidationResult
import com.projectx.householdtasks.presentation.RequestResult
import com.projectx.householdtasks.presentation.event.InviteUserByEmailScreenEvent
import com.projectx.householdtasks.presentation.viewmodel.InviteUserByEmailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class InviteUserByEmailFragment :
    BaseFragment<FragmentInviteUserByEmailBinding, InviteUserByEmailViewModel.InviteUserByEmailUiState, InviteUserByEmailScreenEvent>() {

    override fun getViewBinding() = FragmentInviteUserByEmailBinding.inflate(layoutInflater)

    override fun getBaseViewModel() = viewModel<InviteUserByEmailViewModel>().value

    override fun bindUI() = super.bindUI().apply {
        binding.toolbarLayout.toolbar.setOnClickListener {
            viewModel.onEvent(InviteUserByEmailScreenEvent.NavBack(findNavController()))
        }
        addEmailChangedListener()
        setButtonContinueClickListener()
    }

    private fun addEmailChangedListener() {
        binding.email.addTextChangedListener {
            viewModel.onEvent(InviteUserByEmailScreenEvent.SetEmailValue(it.toString()))
            binding.buttonContinue.isEnabled =
                (viewModel as InviteUserByEmailViewModel).isSaveButtonEnabled()
            viewModel.onEvent(InviteUserByEmailScreenEvent.ResetEmailError)
        }
    }

    override fun FragmentInviteUserByEmailBinding.processState(state: InviteUserByEmailViewModel.InviteUserByEmailUiState) {
        addEmailObserver(state)
        addUiStateObserver(state)
        hideEmailErrorsOnChange(state)
    }

    private fun addEmailObserver(state: InviteUserByEmailViewModel.InviteUserByEmailUiState) {
        state.email.let {
            if (binding.newUserEmail.editText!!.text.toString() != it) {
                binding.newUserEmail.editText!!.setText(it)
            }
            binding.buttonContinue.isEnabled =
                (viewModel as InviteUserByEmailViewModel).isSaveButtonEnabled()
        }
    }

    private fun addUiStateObserver(state: InviteUserByEmailViewModel.InviteUserByEmailUiState) {
        state.let {
            resetError()

            when (it.emailValidationResult) {
                EmailValidationResult.InvalidEmailError -> setErrorForEmail()
                EmailValidationResult.OK -> {}
            }

            when (it.requestResult) {
                RequestResult.Success -> {
                    viewModel.onEvent(
                        InviteUserByEmailScreenEvent.NavigateToProfile(
                            findNavController()
                        )
                    )
                }
                RequestResult.RequestFailedError -> setConnectionError()
                else -> {}
            }
        }
    }

    private fun resetError() {
        binding.newUserEmail.error = ""
    }

    //TODO: add error for failure request
    private fun setConnectionError() {
        binding.newUserEmail.error = getString(R.string.connection_error)
    }

    private fun hideEmailErrorsOnChange(state: InviteUserByEmailViewModel.InviteUserByEmailUiState) {
        state.email.let {
            binding.newUserEmail.isErrorEnabled = false
        }
    }

    private fun setButtonContinueClickListener() {
        binding.buttonContinue.setOnClickListener {
            viewModel.onEvent(InviteUserByEmailScreenEvent.HandleSaveChanges)
        }
    }

    private fun setErrorForEmail() {
        binding.newUserEmail.isErrorEnabled = true
        binding.newUserEmail.error = getString(R.string.email_error)
    }
}
