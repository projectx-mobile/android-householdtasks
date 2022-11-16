package com.projectx.householdtasks.presentation.fragment

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.projectx.householdtasks.R
import com.projectx.householdtasks.databinding.FragmentEditProfilePasswordBinding
import com.projectx.householdtasks.presentation.*
import com.projectx.householdtasks.presentation.viewmodel.EditProfilePasswordViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditProfilePasswordFragment : BaseFragment() {

    private var _binding: FragmentEditProfilePasswordBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<EditProfilePasswordViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfilePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).setBottomNavViewVisibility(false)
        addTextChangeListeners()
        addPasswordsObservers()
        addUiStateObserver()
        setLink()

        binding.apply {
            buttonSaveChanges.setOnClickListener {
                viewModel.handleSaveChanges()
            }
            toolbarLayout.toolbarArrowBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun addTextChangeListeners() {
        binding.currentPassword.addTextChangedListener {
            viewModel.setCurrentPasswordValue(it.toString())
            viewModel.resetCurrentPasswordError()
        }
        binding.newPassword.addTextChangedListener {
            viewModel.setNewPasswordValue(it.toString())
            viewModel.resetNewPasswordError()
        }
        binding.passwordConfirmation.addTextChangedListener {
            viewModel.setPasswordConfirmationValue(it.toString())
            viewModel.resetPasswordConfirmationError()
        }
    }

    private fun addPasswordsObservers() {
        viewModel.currentPassword.observe(viewLifecycleOwner) {
            if (binding.currentPasswordLayout.editText!!.text.toString() != it) {
                binding.currentPasswordLayout.editText!!.setText(it)
            }
            binding.buttonSaveChanges.isEnabled = viewModel.isSaveButtonEnabled()
        }
        viewModel.newPassword.observe(viewLifecycleOwner) {
            if (binding.newPasswordLayout.editText!!.text.toString() != it) {
                binding.newPasswordLayout.editText!!.setText(it)
            }
            binding.buttonSaveChanges.isEnabled = viewModel.isSaveButtonEnabled()
        }
        viewModel.passwordConfirmation.observe(viewLifecycleOwner) {
            if (binding.passwordConfirmationLayout.editText!!.text.toString() != it) {
                binding.passwordConfirmationLayout.editText!!.setText(it)
            }
            binding.buttonSaveChanges.isEnabled = viewModel.isSaveButtonEnabled()
        }
    }

    private fun addUiStateObserver() {
        viewModel.uiState.observe(viewLifecycleOwner) {
            resetErrors()
            binding.helpMessage.visibility = View.INVISIBLE

            when (it.currentPasswordValidationResult) {
                CurrentPasswordValidationResult.LengthError -> setErrorForPassword(binding.currentPasswordLayout)
                CurrentPasswordValidationResult.InvalidPasswordError -> setCurrentPasswordError()
                CurrentPasswordValidationResult.OK -> {}
            }

            when (it.newPasswordValidationResult) {
                NewPasswordValidationResult.LengthError -> setErrorForPassword(binding.newPasswordLayout)
                NewPasswordValidationResult.OK -> {}
            }

            when (it.confirmationValidationResult) {
                PasswordConfirmationValidationResult.LengthError -> setErrorForPassword(binding.passwordConfirmationLayout)
                PasswordConfirmationValidationResult.PasswordsMismatchError -> setErrorForNewPasswords()
                PasswordConfirmationValidationResult.OK -> {}
            }

            when (it.requestResult) {
                RequestResult.Success -> {
                    binding.helpMessage.visibility = View.VISIBLE
                    findNavController().navigate(R.id.profileFragment)
                }
                RequestResult.RequestFailedError -> setConnectionError()
                else -> {}
            }
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
//            TODO: add click
            Toast.makeText(requireContext(), "Link clicked", Toast.LENGTH_SHORT).show()
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.bgColor = ContextCompat.getColor(requireContext(), R.color.white)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val START_PARENT_LINK = 15
        const val END_PARENT_LINK = 36
    }
}
