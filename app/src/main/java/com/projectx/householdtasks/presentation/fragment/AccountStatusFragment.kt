package com.projectx.householdtasks.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.projectx.householdtasks.R
import com.projectx.householdtasks.databinding.FragmentAccountStatusBinding
import com.projectx.householdtasks.presentation.FamilyMember
import com.projectx.householdtasks.presentation.adapter.FamilyMembersAdapter
import com.projectx.householdtasks.presentation.viewmodel.AccountStatusViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountStatusFragment : BaseFragment() {

    private var _binding: FragmentAccountStatusBinding? = null
    private val binding get() = _binding!!
    private var familyMembers: List<FamilyMember>? = null
    private val viewModel by viewModel<AccountStatusViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountStatusBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonParent.isSelected = true
        binding.toolbarLayout.toolbar.setOnClickListener { findNavController().navigateUp() }

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
//            viewModel.setRole(Role.PARENT)
            binding.buttonParent.isSelected = true
            binding.buttonChild.isSelected = false
            binding.buttonChild.setTextColor(
                ContextCompat.getColor(requireContext(), (R.color.dark_text_color))
            )
            binding.buttonParent.setTextColor(
                ContextCompat.getColor(requireContext(), (R.color.white))
            )
        }

        familyMembers = viewModel.createFamilyList()
        val adapter = FamilyMembersAdapter(requireContext(), familyMembers!!)
        binding.recyclerViewFamilyMembersList.adapter = adapter
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewFamilyMembersList.layoutManager = layoutManager
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}

