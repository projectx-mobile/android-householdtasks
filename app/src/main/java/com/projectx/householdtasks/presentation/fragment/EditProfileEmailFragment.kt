package com.projectx.householdtasks.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.projectx.householdtasks.R
import com.projectx.householdtasks.databinding.FragmentEditProfileEmailBinding
import com.projectx.householdtasks.presentation.viewmodel.EditProfileEmailViewModel

class EditProfileEmailFragment : BaseFragment() {

    private var _binding: FragmentEditProfileEmailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: EditProfileEmailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileEmailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[EditProfileEmailViewModel::class.java]
        binding.editProfileEmailViewModel = viewModel
        binding.lifecycleOwner = this
        binding.currentEmailLayout.editText?.setText("name@example.com")
        binding.toolbarLayout.toolbar.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }
        hideEmailErrorsOnChange()
        setButtonContinueClickListener()
    }

    private fun hideEmailErrorsOnChange() {
        viewModel.newEmail.observe(viewLifecycleOwner) {
            binding.newEmailLayout.isErrorEnabled = false
        }
    }

    private fun setButtonContinueClickListener() {
        binding.buttonSaveChanges.setOnClickListener {
            resetError()
            if (viewModel.isEmailValid()) {
                binding.helpMessage.visibility = View.VISIBLE
            } else {
                setErrorForEmail()
            }
        }
    }

    private fun resetError() {
        binding.newEmailLayout.error = null
    }

    private fun setErrorForEmail() {
        binding.newEmailLayout.error = getString(R.string.email_error)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
