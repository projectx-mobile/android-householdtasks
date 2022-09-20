package com.projectx.householdtasks.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.projectx.householdtasks.databinding.FragmentParentHomescreenBinding
import com.projectx.householdtasks.presentation.FamilyMembersListAdapter
import com.projectx.householdtasks.presentation.UpdatesListAdapter
import com.projectx.householdtasks.presentation.viewmodel.ParentHomescreenViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ParentHomescreenFragment : BaseFragment() {

    private var _binding: FragmentParentHomescreenBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<ParentHomescreenViewModel>()

    private val customFamilyMembersAdapter = FamilyMembersListAdapter() {

    }

    private val customUpdatesAdapter = UpdatesListAdapter() {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = FragmentParentHomescreenBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bindUI().subscribeUI()
    }

    private fun FragmentParentHomescreenBinding.bindUI() = this.also {
        viewModel.getFamilyMembers()?.also {
            customFamilyMembersAdapter.submitList(it)
            familyMembersRecyclerView.apply {
                adapter = customFamilyMembersAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        } ?: emptyFamily()


        viewModel.getUpdates()?.also {
            customUpdatesAdapter.submitList(it)
            updatesRecyclerView.apply {
                adapter = customUpdatesAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        } ?: emptyUpdates()

    }

    private fun emptyUpdates() {
        binding.emptyUpdates.visibility = View.VISIBLE
    }

    private fun emptyFamily() {
        binding.emptyFamily.visibility = View.VISIBLE
    }

    private fun FragmentParentHomescreenBinding.subscribeUI() = this.also {

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}