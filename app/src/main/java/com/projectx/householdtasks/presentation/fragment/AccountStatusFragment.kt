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
import com.projectx.householdtasks.presentation.FamilyMembersAdapter
import com.projectx.householdtasks.presentation.Role
import com.projectx.householdtasks.presentation.viewmodel.AccountStatusViewModel
import com.projectx.householdtasks.presentation.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountStatusFragment : BaseFragment() {

    private var _binding: FragmentAccountStatusBinding? = null
    private val binding get() = _binding!!
    private var familyMembers: List<FamilyMember>? = null
//    private val viewModel by viewModel<AccountStatusViewModel>()

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

        familyMembers = createFamilyList()
        val adapter = FamilyMembersAdapter(familyMembers!!)
        binding.recyclerViewFamilyMembersList.adapter = adapter
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewFamilyMembersList.layoutManager = layoutManager
    }

    private fun createFamilyList(): List<FamilyMember> {
        return listOf(
            FamilyMember("Алиса", "А", ContextCompat.getDrawable(requireContext(), R.drawable.oval)!!),
            FamilyMember("Борис", "Б", ContextCompat.getDrawable(requireContext(), R.drawable.oval)!!),
            FamilyMember("Алиса", "А", ContextCompat.getDrawable(requireContext(), R.drawable.oval)!!),
            FamilyMember("Борис", "Б", ContextCompat.getDrawable(requireContext(), R.drawable.oval)!!),
            FamilyMember("Алиса", "А", ContextCompat.getDrawable(requireContext(), R.drawable.oval)!!),
            FamilyMember("Борис", "Б", ContextCompat.getDrawable(requireContext(), R.drawable.oval)!!),
            FamilyMember("Алиса", "А", ContextCompat.getDrawable(requireContext(), R.drawable.oval)!!),
            FamilyMember("Борис", "Б", ContextCompat.getDrawable(requireContext(), R.drawable.oval)!!),
            FamilyMember("Приглашен", null, ContextCompat.getDrawable(requireContext(), R.drawable.button_invited_person)!!),
        )
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}

