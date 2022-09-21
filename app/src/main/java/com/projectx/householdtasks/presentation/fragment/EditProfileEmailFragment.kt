package com.projectx.householdtasks.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.projectx.householdtasks.R
import com.projectx.householdtasks.databinding.FragmentEditProfileEmailBinding
import com.projectx.householdtasks.presentation.viewmodel.EditProfileEmailViewModel

class EditProfileEmailFragment : BaseFragment() {

    private var _binding: FragmentEditProfileEmailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: EditProfileEmailViewModel
    private var onBackPressedListener: OnBackPressedListener? = null

    interface OnBackPressedListener {
        fun onBackButtonPressed()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onBackPressedListener = activity as? OnBackPressedListener
    }

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
        hideEmailErrorsOnChange()
        setButtonContinueClickListener()
        binding.toolbarLayout.toolbar.setNavigationOnClickListener {
            Toast.makeText(requireContext(), "sdfsdlsdkcmsldk", Toast.LENGTH_SHORT).show()
//            requireActivity().onBackPressed()
            onBackPressedListener!!.onBackButtonPressed()
        }
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
        onBackPressedListener = null
    }
}