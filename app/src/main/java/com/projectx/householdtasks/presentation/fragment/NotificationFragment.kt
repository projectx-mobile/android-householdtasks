package com.projectx.householdtasks.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.projectx.householdtasks.R
import com.projectx.householdtasks.databinding.FragmentNotificationBinding
import com.projectx.householdtasks.presentation.FamilyMember
import com.projectx.householdtasks.presentation.FamilyMembersAdapter

class NotificationFragment : BaseFragment() {

    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!
    private var familyMembers: List<FamilyMember>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.toolbarLayout.toolbar.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.notificationPicker.setOnClickListener {
            val bottomSheetFragment = BottomSheetNotificationFragment()
            bottomSheetFragment.show(childFragmentManager, "BottomSheet")
        }

        familyMembers = createFamilyList()
        val adapter = FamilyMembersAdapter(familyMembers!!)
        binding.recyclerViewNotification.adapter = adapter
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewNotification.layoutManager = layoutManager
    }

    private fun createFamilyList(): List<FamilyMember> {
        return listOf(
            FamilyMember("Алиса", "А", ContextCompat.getDrawable(requireContext(), R.drawable.oval)!!),
            FamilyMember("Борис", "Б", ContextCompat.getDrawable(requireContext(), R.drawable.oval)!!),
            FamilyMember("Алиса", "А", ContextCompat.getDrawable(requireContext(), R.drawable.oval)!!),
            FamilyMember("Борис", "Б", ContextCompat.getDrawable(requireContext(), R.drawable.oval)!!),
            FamilyMember("Алиса", "А", ContextCompat.getDrawable(requireContext(), R.drawable.oval)!!),
            FamilyMember("Борис", "Б", ContextCompat.getDrawable(requireContext(), R.drawable.oval)!!),
            FamilyMember("Алиса", "А", ContextCompat.getDrawable(requireContext(), R.drawable.oval)!!),
            FamilyMember("Борис", "Б", ContextCompat.getDrawable(requireContext(), R.drawable.oval)!!),
            FamilyMember("Приглашен", null, ContextCompat.getDrawable(requireContext(), R.drawable.button_invited_person)!!),
        )
    }

    override fun onDestroyView() {
        _binding = null
        familyMembers = null
        super.onDestroyView()
    }
}