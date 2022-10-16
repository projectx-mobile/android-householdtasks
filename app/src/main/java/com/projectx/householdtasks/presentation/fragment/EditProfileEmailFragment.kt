package com.projectx.householdtasks.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.projectx.householdtasks.R
import com.projectx.householdtasks.databinding.FragmentEditProfileEmailBinding
import com.projectx.householdtasks.presentation.EmailValidationResult
import com.projectx.householdtasks.presentation.RequestResult
import com.projectx.householdtasks.presentation.viewmodel.EditProfileEmailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditProfileEmailFragment : BaseFragment() {

    private var _binding: FragmentEditProfileEmailBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<EditProfileEmailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileEmailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        TODO: set current email
        binding.currentEmailLayout.editText?.setText("name@example.com")

        addEmailChangedListener()
        addEmailObserver()
        addUiStateObserver()

        binding.apply {
            toolbarLayout.toolbar.setOnClickListener {
                findNavController().navigateUp()
            }
            buttonSaveChanges.setOnClickListener {
                viewModel.handleSaveChanges()
            }
        }
    }

    private fun addEmailChangedListener() {
        binding.newEmail.addTextChangedListener {
            viewModel.setNewEmailValue(it.toString())
            binding.buttonSaveChanges.isEnabled = viewModel.isSaveButtonEnabled()
            viewModel.resetEmailError()
        }
    }

    private fun addEmailObserver() {
        viewModel.newEmail.observe(viewLifecycleOwner) {
            if (binding.newEmailLayout.editText!!.text.toString() != it) {
                binding.newEmailLayout.editText!!.setText(it)
            }
            binding.buttonSaveChanges.isEnabled = viewModel.isSaveButtonEnabled()
        }
    }

    private fun addUiStateObserver() {
        viewModel.uiState.observe(viewLifecycleOwner) {
            resetError()
            binding.helpMessage.visibility = View.INVISIBLE

            when (it.emailValidationResult) {
                EmailValidationResult.InvalidEmailError -> setErrorForEmail()
                EmailValidationResult.OK -> {}
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
