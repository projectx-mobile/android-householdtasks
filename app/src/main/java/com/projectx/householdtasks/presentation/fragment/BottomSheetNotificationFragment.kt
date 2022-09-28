package com.projectx.householdtasks.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.projectx.householdtasks.databinding.FragmentBottomSheetNotificationBinding


class BottomSheetNotificationFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetNotificationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.exitBottomSheet.setOnClickListener {
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}