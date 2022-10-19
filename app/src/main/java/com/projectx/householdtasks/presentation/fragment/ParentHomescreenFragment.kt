package com.projectx.householdtasks.presentation.fragment

import android.view.View
import android.view.View.OnClickListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.projectx.householdtasks.databinding.FragmentParentHomescreenBinding
import com.projectx.householdtasks.presentation.adapter.FamilyMembersListAdapter
import com.projectx.householdtasks.presentation.adapter.UpdatesListAdapter
import com.projectx.householdtasks.presentation.event.ParentHomeScreenEvent
import com.projectx.householdtasks.presentation.viewmodel.ParentHomescreenViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ParentHomescreenFragment :
    BaseFragment<FragmentParentHomescreenBinding, ParentHomescreenViewModel.ParentHomeScreenUiState, ParentHomeScreenEvent>() {

    override fun getViewBinding() = FragmentParentHomescreenBinding.inflate(layoutInflater)

    override fun getBaseViewModel() = viewModel<ParentHomescreenViewModel>().value

    private val navigateToAllUpdatesFragment by lazy {
        OnClickListener {
            viewModel.onEvent(ParentHomeScreenEvent.NavigateToAllUpdates(findNavController()))
        }
    }

    private val customFamilyMembersAdapter = FamilyMembersListAdapter {}
    private val customUpdatesAdapter = UpdatesListAdapter {}

    override fun bindUI() = super.bindUI().apply {
        familyMembersRecyclerView.apply {
            adapter = customFamilyMembersAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        updatesRecyclerView.apply {
            adapter = customUpdatesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        viewAllUpdates.setOnClickListener(navigateToAllUpdatesFragment)
        viewAllUpdatesIcon.setOnClickListener(navigateToAllUpdatesFragment)
    }

    override fun FragmentParentHomescreenBinding.processState(state: ParentHomescreenViewModel.ParentHomeScreenUiState) {
        customFamilyMembersAdapter.submitList(state.familyMemberTests)
        customUpdatesAdapter.submitList(state.updates)

        if (state.familyMemberTests.isEmpty()) emptyFamily()
        if (state.updates.isEmpty()) emptyUpdates()
    }

    private fun emptyUpdates() {
        binding.emptyUpdates.visibility = View.VISIBLE
    }

    private fun emptyFamily() {
        binding.emptyFamily.visibility = View.VISIBLE
    }
}