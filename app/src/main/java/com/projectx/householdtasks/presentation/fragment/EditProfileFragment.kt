package com.projectx.householdtasks.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.projectx.householdtasks.R
import com.projectx.householdtasks.databinding.FragmentEditProfileBinding
import com.projectx.householdtasks.presentation.viewmodel.EditProfileViewModel

class EditProfileFragment: BaseFragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: EditProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[EditProfileViewModel::class.java]
        binding.editProfileViewModel = viewModel
        binding.lifecycleOwner = this
        binding.name.setText("Марго")
//        if (viewModel.isNameValid()) {
            binding.textViewSaveChanges.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_text_color))
            binding.saveChanges.setBackgroundResource(R.drawable.rectangle_white_with_shadow)
            binding.saveChanges.setOnClickListener {
            binding.saveChanges.setBackgroundResource(R.drawable.rectangle)
            requireActivity().onBackPressed()
//            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}