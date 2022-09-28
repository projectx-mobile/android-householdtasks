package com.projectx.householdtasks.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.projectx.householdtasks.R
import com.projectx.householdtasks.databinding.FragmentInviteUserByEmailBinding
import com.projectx.householdtasks.presentation.viewmodel.InviteUserByEmailViewModel


class InviteUserByEmailFragment : BaseFragment() {
    private lateinit var viewModel: InviteUserByEmailViewModel
    private var _binding: FragmentInviteUserByEmailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInviteUserByEmailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[InviteUserByEmailViewModel::class.java]
        binding.inviteUserByEmailViewModel = viewModel
        binding.lifecycleOwner = this

        binding.toolbarLayout.toolbar.setOnClickListener {
            findNavController().navigateUp()
        }

        hideEmailErrorsOnChange()
        setButtonContinueClickListener()
    }

    private fun hideEmailErrorsOnChange() {
        viewModel.email.observe(viewLifecycleOwner) {
            binding.newUserEmail.isErrorEnabled = false
        }
    }

    private fun setButtonContinueClickListener() {
        binding.buttonContinue.setOnClickListener {
            resetErrors()
            if (!viewModel.isEmailValid()) {
                setErrorForEmail()
            }
            if (viewModel.isEmailValid()) {
                Toast.makeText(context, getString(R.string.send_invitation), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun resetErrors() {
        binding.newUserEmail.isErrorEnabled = false
    }

    private fun setErrorForEmail() {
        binding.newUserEmail.isErrorEnabled = true
        binding.newUserEmail.error = getString(R.string.email_error)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
