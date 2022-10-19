package com.projectx.householdtasks.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.projectx.householdtasks.R
import com.projectx.householdtasks.databinding.FragmentInviteUserBinding

class InviteUserFragment : BaseFragment() {
    private var _binding: FragmentInviteUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInviteUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.inviteByEmail.setOnClickListener {
            findNavController().navigate(R.id.action_inviteUserFragment_to_inviteUserByEmailFragment)
        }

        binding.shareLink.setOnClickListener {
        }

        binding.familyId.setOnClickListener {}
        binding.toolbarLayout.toolbarArrowBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

