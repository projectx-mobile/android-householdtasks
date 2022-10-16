package com.projectx.householdtasks.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.projectx.householdtasks.R
import com.projectx.householdtasks.databinding.FragmentInviteUserByEmailBinding
import com.projectx.householdtasks.presentation.EmailValidationResult
import com.projectx.householdtasks.presentation.RequestResult
import com.projectx.householdtasks.presentation.viewmodel.InviteUserByEmailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class InviteUserByEmailFragment : BaseFragment() {

    private var _binding: FragmentInviteUserByEmailBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<InviteUserByEmailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInviteUserByEmailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarLayout.toolbar.setOnClickListener {
            findNavController().navigateUp()
        }

        addEmailChangedListener()
        addEmailObserver()
        addUiStateObserver()

        hideEmailErrorsOnChange()
        setButtonContinueClickListener()
    }

    private fun addEmailChangedListener() {
        binding.email.addTextChangedListener {
            viewModel.setEmailValue(it.toString())
            binding.buttonContinue.isEnabled = viewModel.isSaveButtonEnabled()
            viewModel.resetEmailError()
        }
    }

    private fun addEmailObserver() {
        viewModel.email.observe(viewLifecycleOwner) {
            if (binding.newUserEmail.editText!!.text.toString() != it) {
                binding.newUserEmail.editText!!.setText(it)
            }
            binding.buttonContinue.isEnabled = viewModel.isSaveButtonEnabled()
        }
    }

    private fun addUiStateObserver() {
        viewModel.uiState.observe(viewLifecycleOwner) {
            resetError()

            when (it.emailValidationResult) {
                EmailValidationResult.InvalidEmailError -> setErrorForEmail()
                EmailValidationResult.OK -> {}
            }

            when (it.requestResult) {
                RequestResult.Success -> {
                    findNavController().navigate(R.id.profileFragment)
                }
                RequestResult.RequestFailedError -> setConnectionError()
                else -> {}
            }
        }
    }

    private fun resetError() {
        binding.newUserEmail.error = ""
    }

    //    TODO: add error for failure request
    private fun setConnectionError() {
        binding.newUserEmail.error = getString(R.string.connection_error)
    }

    private fun hideEmailErrorsOnChange() {
        viewModel.email.observe(viewLifecycleOwner) {
            binding.newUserEmail.isErrorEnabled = false
        }
    }

    private fun setButtonContinueClickListener() {
        binding.buttonContinue.setOnClickListener {
            viewModel.handleSaveChanges()
        }
    }

    private fun setErrorForEmail() {
        binding.newUserEmail.isErrorEnabled = true
        binding.newUserEmail.error = getString(R.string.email_error)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
