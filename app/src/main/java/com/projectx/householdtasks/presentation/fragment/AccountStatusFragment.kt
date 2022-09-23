package com.projectx.householdtasks.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.projectx.householdtasks.R
import com.projectx.householdtasks.databinding.FragmentAccountStatusBinding

class AccountStatusFragment : BaseFragment() {

    private var _binding: FragmentAccountStatusBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountStatusBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.buttonParent.isSelected = true

        binding.toolbarLayout.toolbar.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }

        binding.buttonChild.setOnClickListener {
            binding.buttonParent.setTextColor(
                ContextCompat.getColor(requireContext(), (R.color.dark_text_color))
            )
            binding.buttonChild.setTextColor(
                ContextCompat.getColor(requireContext(), (R.color.white))
            )
            binding.buttonChild.isSelected = true
            binding.buttonParent.isSelected = false

        }
        binding.buttonParent.setOnClickListener {
            binding.buttonParent.isSelected = true
            binding.buttonChild.isSelected = false
            binding.buttonChild.setTextColor(
                ContextCompat.getColor(requireContext(), (R.color.dark_text_color))
            )
            binding.buttonParent.setTextColor(
                ContextCompat.getColor(requireContext(), (R.color.white))
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

