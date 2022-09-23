package com.projectx.householdtasks.presentation.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.projectx.householdtasks.databinding.FragmentInviteUserBinding
import com.projectx.householdtasks.databinding.FragmentProfileBinding

class InviteUserFragment : BaseFragment() {
    private var _binding: FragmentInviteUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInviteUserBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.inviteByEmail.setOnClickListener {}

        binding.shareLink.setOnClickListener {}

        binding.familyId.setOnClickListener {}
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

