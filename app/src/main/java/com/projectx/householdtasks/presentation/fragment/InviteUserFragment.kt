package com.projectx.householdtasks.presentation.fragment

import androidx.navigation.fragment.findNavController
import com.projectx.householdtasks.databinding.FragmentInviteUserBinding
import com.projectx.householdtasks.presentation.event.InviteUserScreenEvent
import com.projectx.householdtasks.presentation.viewmodel.InviteUserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class InviteUserFragment :
    BaseFragment<FragmentInviteUserBinding, Nothing, InviteUserScreenEvent>() {

    override fun getViewBinding() = FragmentInviteUserBinding.inflate(layoutInflater)

    override fun getBaseViewModel() = viewModel<InviteUserViewModel>().value

    override fun bindUI() = super.bindUI().apply {
        binding.inviteByEmail.setOnClickListener {
            viewModel.onEvent(InviteUserScreenEvent.NavigateToInviteUserByEmail(findNavController()))
        }

        binding.shareLink.setOnClickListener {
        }

        binding.familyId.setOnClickListener {}
        binding.toolbarLayout.toolbar.setOnClickListener {
            viewModel.onEvent(InviteUserScreenEvent.NavBack(findNavController()))
        }
    }
}