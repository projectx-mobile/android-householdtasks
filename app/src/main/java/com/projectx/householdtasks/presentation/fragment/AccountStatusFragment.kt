package com.projectx.householdtasks.presentation.fragment

import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.projectx.householdtasks.R
import com.projectx.householdtasks.databinding.FragmentAccountStatusBinding
import com.projectx.householdtasks.presentation.FamilyMember
import com.projectx.householdtasks.presentation.adapter.FamilyMembersAdapter
import com.projectx.householdtasks.presentation.event.NavEvent
import com.projectx.householdtasks.presentation.viewmodel.AccountStatusViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountStatusFragment :
    BaseFragment<FragmentAccountStatusBinding, List<FamilyMember>, NavEvent>() {

    override fun getViewBinding() = FragmentAccountStatusBinding.inflate(layoutInflater)

    override fun getBaseViewModel() = viewModel<AccountStatusViewModel>().value

    override fun bindUI() = super.bindUI().apply {
        buttonParent.isSelected = true
        toolbarLayout.toolbar.setOnClickListener {
            viewModel.onEvent(NavEvent.NavBack(findNavController()))
        }

        buttonChild.setOnClickListener {
            buttonParent.setTextColor(
                ContextCompat.getColor(requireContext(), (R.color.dark_text_color))
            )
            buttonChild.setTextColor(
                ContextCompat.getColor(requireContext(), (R.color.white))
            )
            buttonChild.isSelected = true
            buttonParent.isSelected = false
        }
        buttonParent.setOnClickListener {
            buttonParent.isSelected = true
            buttonChild.isSelected = false

            buttonChild.setTextColor(
                ContextCompat.getColor(requireContext(), (R.color.dark_text_color))
            )
            buttonParent.setTextColor(
                ContextCompat.getColor(requireContext(), (R.color.white))
            )
        }
    }

    override fun FragmentAccountStatusBinding.processState(state: List<FamilyMember>) {
        recyclerViewFamilyMembersList.apply {
            adapter = FamilyMembersAdapter(requireContext(), state)
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}

