package com.projectx.householdtasks.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.textfield.TextInputLayout
import com.projectx.householdtasks.R
import com.projectx.householdtasks.databinding.FragmentEditProfileEmailBinding
import com.projectx.householdtasks.databinding.FragmentEditProfilePasswordBinding


class EditProfilePasswordFragment : BaseFragment() {

    private var _binding: FragmentEditProfilePasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfilePasswordBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    private fun checkPasswordsMatch(): Boolean {
        if (binding.newPassword.text.toString() != binding.passwordConfirmation.text.toString()) {
            setErrorForPassword()
            return false
        }
        return true
    }

    private fun setErrorForPassword() {
        binding.currentPassword.error = getString(R.string.password_error)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}