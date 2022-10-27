package com.projectx.parent.presentation.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.projectx.common.presentation.fragment.BaseFragment
import com.projectx.householdtasks.parent.databinding.FragmentParentHomescreenBinding
//import com.projectx.parent.databinding.FragmentParentHomescreenBinding
import com.projectx.householdtasks.parent.presentation.adapter.FamilyMembersListAdapter
import com.projectx.householdtasks.parent.presentation.adapter.UpdatesListAdapter
import com.projectx.parent.presentation.viewmodel.ParentHomescreenViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ParentHomescreenFragment :
    BaseFragment<FragmentParentHomescreenBinding, ParentHomescreenViewModel>(
        FragmentParentHomescreenBinding::inflate
    ) {

    override val viewModel by viewModel<ParentHomescreenViewModel>()

    private val customFamilyMembersAdapter = FamilyMembersListAdapter {}

    private val customUpdatesAdapter = UpdatesListAdapter {}

    override fun FragmentParentHomescreenBinding.bindUI() {
        familyMembersRecyclerView.apply {
            adapter = customFamilyMembersAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        updatesRecyclerView.apply {
            adapter = customUpdatesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewAllUpdates.setOnClickListener {
            viewModel.navigateToAllUpdates()
        }
        viewAllUpdatesIcon.setOnClickListener {
            viewModel.navigateToAllUpdates()
        }
    }

    override fun ParentHomescreenViewModel.subscribeUI() {
        viewModel.getFamilyMembers()?.also {
            customFamilyMembersAdapter.submitList(it)
        } ?: emptyFamily()

        viewModel.getUpdates()?.also {
            customUpdatesAdapter.submitList(it)
        } ?: emptyUpdates()
    }

    private fun emptyUpdates() {
        binding.emptyUpdates.visibility = View.VISIBLE
    }

    private fun emptyFamily() {
        binding.emptyFamily.visibility = View.VISIBLE
    }
}