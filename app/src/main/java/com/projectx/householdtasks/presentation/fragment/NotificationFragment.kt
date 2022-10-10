package com.projectx.householdtasks.presentation.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.projectx.householdtasks.R
import com.projectx.householdtasks.databinding.FragmentNotificationBinding
import com.projectx.householdtasks.presentation.FamilyMember
import com.projectx.householdtasks.presentation.FamilyMembersAdapter
import com.projectx.householdtasks.presentation.viewmodel.NotificationSharedViewModel
import com.projectx.householdtasks.presentation.viewmodel.NotificationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationFragment : BaseFragment() {

    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    private var bottomSheetFragment: BottomSheetNotificationFragment? = null
    private var familyMembers: List<FamilyMember>? = null

    private val sharedViewModel: NotificationSharedViewModel by activityViewModels()
    private val viewModel by viewModel<NotificationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)

        sharedViewModel.interval.observe(viewLifecycleOwner) { interval ->
            binding.textViewInterval.text = interval
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarLayout.toolbar.setOnClickListener {
            findNavController().navigateUp()
        }

        addScrollListener()

        bottomSheetFragment = BottomSheetNotificationFragment()
        binding.notificationPicker.setOnClickListener {
            bottomSheetFragment?.show(childFragmentManager, "BottomSheet")
        }

        familyMembers = createFamilyList()
        setAdapter()
    }

    private fun addScrollListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.nestedScrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                if (scrollY > 0) {
                    binding.appBarLayout.visibility = View.VISIBLE
                } else {
                    binding.appBarLayout.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun createFamilyList(): List<FamilyMember> {
        return listOf(
            FamilyMember(
                "Алиса",
                "А",
                ContextCompat.getDrawable(requireContext(), R.drawable.oval)!!
            ),
            FamilyMember(
                "Борис",
                "Б",
                ContextCompat.getDrawable(requireContext(), R.drawable.oval)!!
            ),
            FamilyMember(
                "Алиса",
                "А",
                ContextCompat.getDrawable(requireContext(), R.drawable.oval)!!
            ),
            FamilyMember(
                "Борис",
                "Б",
                ContextCompat.getDrawable(requireContext(), R.drawable.oval)!!
            ),
            FamilyMember(
                "Алиса",
                "А",
                ContextCompat.getDrawable(requireContext(), R.drawable.oval)!!
            ),
            FamilyMember(
                "Борис",
                "Б",
                ContextCompat.getDrawable(requireContext(), R.drawable.oval)!!
            ),
            FamilyMember(
                "Алиса",
                "А",
                ContextCompat.getDrawable(requireContext(), R.drawable.oval)!!
            ),
            FamilyMember(
                "Борис",
                "Б",
                ContextCompat.getDrawable(requireContext(), R.drawable.oval)!!
            ),
            FamilyMember(
                "Приглашен",
                null,
                ContextCompat.getDrawable(requireContext(), R.drawable.button_invited_person)!!
            ),
        )
    }

    private fun setAdapter() {
        val adapter = FamilyMembersAdapter(familyMembers!!)
        binding.recyclerViewNotification.adapter = adapter
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewNotification.layoutManager = layoutManager
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        familyMembers = null
    }
}