package com.projectx.common.presentation.fragment

import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.projectx.common.R
import com.projectx.common.databinding.FragmentAccountStatusBinding
import com.projectx.common.presentation.adapter.FamilyMembersAdapter
import com.projectx.common.presentation.viewmodel.AccountStatusViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountStatusFragment :
    BaseFragment<FragmentAccountStatusBinding, AccountStatusViewModel>(FragmentAccountStatusBinding::inflate) {

    override val viewModel by viewModel<AccountStatusViewModel>()
    private val familyMembersAdapter by lazy { FamilyMembersAdapter(requireContext()) }

    override fun FragmentAccountStatusBinding.bindUI() {
        buttonParent.isSelected = true

        toolbarLayout.toolbarArrowBack.setOnClickListener {
            viewModel.onToolbarArrowClick()
        }

        buttonChild.setOnClickListener {
            buttonParent.setTextColor(getColor(R.color.primary_midnight))
            buttonChild.setTextColor(getColor(R.color.white))
            buttonChild.isSelected = true
            buttonParent.isSelected = false
        }
        buttonParent.setOnClickListener {
//            viewModel.setRole(Role.PARENT)
            buttonParent.isSelected = true
            buttonChild.isSelected = false
            buttonChild.setTextColor(getColor(R.color.primary_midnight))
            buttonParent.setTextColor(getColor(R.color.white))
        }

        recyclerViewFamilyMembersList.apply {
            adapter = familyMembersAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun getColor(@ColorRes id: Int) =
        ContextCompat.getColor(requireContext(), id)

    override fun AccountStatusViewModel.subscribeUI() {
        familyMembers.observeUiState {
            familyMembersAdapter.submitList(it)
        }
    }
}