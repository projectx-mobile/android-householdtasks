package com.projectx.householdtasks.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.projectx.householdtasks.databinding.FragmentInviteUserBinding
import com.projectx.householdtasks.databinding.FragmentSupportScreenBinding

class SupportScreenFragment : Fragment() {
    private var _binding: FragmentSupportScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSupportScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.faq.setOnClickListener {}

        binding.privacyPolicy.setOnClickListener {}

        binding.feedback.setOnClickListener {}
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}