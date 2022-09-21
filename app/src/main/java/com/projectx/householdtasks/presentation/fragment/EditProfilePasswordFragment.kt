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
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.projectx.householdtasks.R
import com.projectx.householdtasks.databinding.FragmentEditProfilePasswordBinding
import com.projectx.householdtasks.presentation.viewmodel.EditProfileEmailViewModel
import com.projectx.householdtasks.presentation.viewmodel.EditProfilePasswordViewModel

class EditProfilePasswordFragment : BaseFragment() {

    private var _binding: FragmentEditProfilePasswordBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: EditProfilePasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfilePasswordBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[EditProfilePasswordViewModel::class.java]
        binding.editProfilePasswordViewModel = viewModel
        binding.lifecycleOwner = this
        binding.currentPasswordLayout.isErrorEnabled = false
        binding.newPasswordLayout.isErrorEnabled = false

        hidePasswordErrorsOnChange()
        setLink()

        binding.buttonSaveChanges.setOnClickListener {
            resetErrors()
            if (viewModel.isPasswordsMatch() && viewModel.isValid()) {
                binding.helpMessage.visibility = View.VISIBLE
            } else {
                if (!viewModel.isPasswordsMatch()) {
                    setErrorForNewPasswords()
                }
                if (!viewModel.isCurrentPasswordValid()) {
                    setErrorForPassword(binding.currentPasswordLayout)
                }
                if (!viewModel.isNewPasswordValid()) {
                    setErrorForPassword(binding.newPasswordLayout)
                }
                if (!viewModel.isPasswordConfirmationValid()) {
                    setErrorForPassword(binding.passwordConfirmationLayout)
                }
            }
        }
    }

    private fun setLink() {
        val spannableString = SpannableString(binding.helpLink.text)
        spannableString.setSpan(MyClickableSpan(), 15, 36, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.helpLink.text = spannableString
        binding.helpLink.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun hidePasswordErrorsOnChange() {
        viewModel.currentPassword.observe(viewLifecycleOwner) {
            binding.currentPasswordLayout.isErrorEnabled = false
        }
        viewModel.newPassword.observe(viewLifecycleOwner) {
            binding.newPasswordLayout.isErrorEnabled = false
        }
        viewModel.passwordConfirmation.observe(viewLifecycleOwner) {
            binding.passwordConfirmationLayout.error = null
        }
    }
    private fun resetErrors() {
        binding.currentPasswordLayout.isErrorEnabled = false
        binding.newPasswordLayout.isErrorEnabled = false
        binding.passwordConfirmationLayout.error = null
    }

    private fun setErrorForNewPasswords() {
        binding.newPasswordLayout.error = " "
        binding.passwordConfirmationLayout.error = "Введенные пароли не совпадают"
    }

//    private fun isPasswordsMatch(): Boolean {
//        if (binding.newPassword.text.toString() != binding.passwordConfirmation.text.toString()) {
//            setErrorForPassword()
//            return false
//        }
//        return true
//    }

    private fun setErrorForPassword(password: TextInputLayout) {
        password.error = getString(R.string.password_error)
    }

    inner class MyClickableSpan : ClickableSpan() {
        override fun onClick(widget: View) {
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
}