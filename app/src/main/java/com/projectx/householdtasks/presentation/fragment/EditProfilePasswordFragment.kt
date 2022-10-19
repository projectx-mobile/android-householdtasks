package com.projectx.householdtasks.presentation.fragment

import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.projectx.householdtasks.R
import com.projectx.householdtasks.databinding.FragmentEditProfilePasswordBinding
import com.projectx.householdtasks.presentation.CurrentPasswordValidationResult
import com.projectx.householdtasks.presentation.NewPasswordValidationResult
import com.projectx.householdtasks.presentation.PasswordConfirmationValidationResult
import com.projectx.householdtasks.presentation.RequestResult
import com.projectx.householdtasks.presentation.event.EditProfilePasswordScreenEvent
import com.projectx.householdtasks.presentation.viewmodel.EditProfilePasswordViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditProfilePasswordFragment :
    BaseFragment<FragmentEditProfilePasswordBinding, EditProfilePasswordViewModel.EditProfilePasswordUiState, EditProfilePasswordScreenEvent>() {

    override fun getViewBinding() = FragmentEditProfilePasswordBinding.inflate(layoutInflater)

    override fun getBaseViewModel() = viewModel<EditProfilePasswordViewModel>().value

    override fun bindUI() = super.bindUI().apply {
        addTextChangeListeners()
        setLink()

        buttonSaveChanges.setOnClickListener {
            viewModel.onEvent(EditProfilePasswordScreenEvent.HandleSaveChanges)
        }
        toolbarLayout.toolbar.setOnClickListener {
            viewModel.onEvent(EditProfilePasswordScreenEvent.NavBack(findNavController()))
        }
    }

    private fun addTextChangeListeners() {
        binding.currentPassword.addTextChangedListener {
            viewModel.onEvent(EditProfilePasswordScreenEvent.SetCurrentPasswordValue(it.toString()))
            viewModel.onEvent(EditProfilePasswordScreenEvent.ResetCurrentPasswordError)
        }
        binding.newPassword.addTextChangedListener {
            viewModel.onEvent(EditProfilePasswordScreenEvent.SetNewPasswordValue(it.toString()))
            viewModel.onEvent(EditProfilePasswordScreenEvent.ResetNewPasswordError)
        }
        binding.passwordConfirmation.addTextChangedListener {
            viewModel.onEvent(EditProfilePasswordScreenEvent.SetPasswordConfirmationValue(it.toString()))
            viewModel.onEvent(EditProfilePasswordScreenEvent.ResetPasswordConfirmationError)
        }
    }

    override fun FragmentEditProfilePasswordBinding.processState(state: EditProfilePasswordViewModel.EditProfilePasswordUiState) {
        addPasswordsObservers(state)
        addUiStateObserver(state)
    }

    private fun addPasswordsObservers(state: EditProfilePasswordViewModel.EditProfilePasswordUiState) {
        val isSaveButtonEnabled = (viewModel as EditProfilePasswordViewModel).isSaveButtonEnabled()
        state.currentPassword.let {
            if (binding.currentPasswordLayout.editText!!.text.toString() != it) {
                binding.currentPasswordLayout.editText!!.setText(it)
            }
            binding.buttonSaveChanges.isEnabled = isSaveButtonEnabled
        }
        state.newPassword.let {
            if (binding.newPasswordLayout.editText!!.text.toString() != it) {
                binding.newPasswordLayout.editText!!.setText(it)
            }
            binding.buttonSaveChanges.isEnabled = isSaveButtonEnabled
        }
        state.passwordConfirmation.let {
            if (binding.passwordConfirmationLayout.editText!!.text.toString() != it) {
                binding.passwordConfirmationLayout.editText!!.setText(it)
            }
            binding.buttonSaveChanges.isEnabled = isSaveButtonEnabled
        }
    }

    private fun addUiStateObserver(state: EditProfilePasswordViewModel.EditProfilePasswordUiState) {
        resetErrors()
        binding.helpMessage.visibility = View.INVISIBLE

        when (state.currentPasswordValidationResult) {
            CurrentPasswordValidationResult.LengthError -> setErrorForPassword(binding.currentPasswordLayout)
            CurrentPasswordValidationResult.InvalidPasswordError -> setCurrentPasswordError()
            CurrentPasswordValidationResult.OK -> {}
        }

        when (state.newPasswordValidationResult) {
            NewPasswordValidationResult.LengthError -> setErrorForPassword(binding.newPasswordLayout)
            NewPasswordValidationResult.OK -> {}
        }

        when (state.confirmationValidationResult) {
            PasswordConfirmationValidationResult.LengthError -> setErrorForPassword(binding.passwordConfirmationLayout)
            PasswordConfirmationValidationResult.PasswordsMismatchError -> setErrorForNewPasswords()
            PasswordConfirmationValidationResult.OK -> {}
        }

        when (state.requestResult) {
            RequestResult.Success -> {
                binding.helpMessage.visibility = View.VISIBLE
                findNavController().navigate(R.id.profileFragment)
            }
            RequestResult.RequestFailedError -> setConnectionError()
            else -> {}
        }
    }

    private fun setLink() {
        val spannableString = SpannableString(binding.helpLink.text)
        spannableString.setSpan(
            HelpMessageClickableSpan(),
            START_PARENT_LINK,
            END_PARENT_LINK,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.helpLink.text = spannableString
        binding.helpLink.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun resetErrors() {
        binding.currentPasswordLayout.isErrorEnabled = false
        binding.newPasswordLayout.isErrorEnabled = false
        binding.passwordConfirmationLayout.error = null
    }

    private fun setErrorForNewPasswords() {
        binding.newPasswordLayout.error = " "
        binding.passwordConfirmationLayout.error = getString(R.string.passwords_not_match)
    }

    private fun setErrorForPassword(password: TextInputLayout) {
        password.error = getString(R.string.password_error)
    }

    private fun setCurrentPasswordError() {
        binding.currentPasswordLayout.error = getString(R.string.wrong_password)
    }

    //    todo add error for failure request
    private fun setConnectionError() {
        binding.passwordConfirmationLayout.error = getString(R.string.connection_error)
    }

    inner class HelpMessageClickableSpan : ClickableSpan() {
        override fun onClick(widget: View) {
            //TODO: add click
            Toast.makeText(requireContext(), "Link clicked", Toast.LENGTH_SHORT).show()
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.bgColor = ContextCompat.getColor(requireContext(), R.color.white)
        }
    }

    companion object {
        const val START_PARENT_LINK = 15
        const val END_PARENT_LINK = 36
    }
}
