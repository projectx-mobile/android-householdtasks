package com.projectx.householdtasks.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.projectx.householdtasks.databinding.FragmentNotificationBinding
import com.projectx.householdtasks.presentation.MainActivity
import com.projectx.householdtasks.presentation.adapter.FamilyMembersAdapter
import com.projectx.householdtasks.presentation.adapter.NotificationSwitchersAdapter
import com.projectx.householdtasks.presentation.adapter.NotificationSwitchersModel
import com.projectx.householdtasks.presentation.viewmodel.NotificationSharedViewModel
import com.projectx.householdtasks.presentation.viewmodel.NotificationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationFragment : BaseFragment() {

    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    private var bottomSheetFragment: BottomSheetNotificationFragment? = null

    private val sharedViewModel: NotificationSharedViewModel by activityViewModels()
    private val viewModel by viewModel<NotificationViewModel>()

    private lateinit var switchesListAdapter: NotificationSwitchersAdapter

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
        (requireActivity() as MainActivity).setBottomNavViewVisibility(false)
        binding.apply {
            switchesListAdapter =
                NotificationSwitchersAdapter(requireContext(), NotificationSwitchersListener())
            recyclerViewSwitches.adapter = switchesListAdapter
            switchesListAdapter.submitList(viewModel.getSwitchersList())

            appbarLayout.setOnClickListener {
                findNavController().navigateUp()
            }
        }

        bottomSheetFragment = BottomSheetNotificationFragment()
        binding.notificationPicker.setOnClickListener {
            bottomSheetFragment?.show(childFragmentManager, "BottomSheet")
        }

        setAdapter()

        var appBarHeight: Int
        binding.appbarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            when (verticalOffset) {
                0 -> {
                    appBarHeight = binding.collapsingToolbar.height
                    binding.constraintLayout.translationY = appBarHeight.toFloat()
                }
                else -> {
                    appBarHeight = binding.collapsingToolbar.height
                    binding.constraintLayout.translationY = appBarHeight + verticalOffset.toFloat()
                }
            }
        }
        )
    }

    private fun setAdapter() {
        val adapter = FamilyMembersAdapter(requireContext(), viewModel.getFamilyList())
        binding.recyclerViewNotification.adapter = adapter
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewNotification.layoutManager = layoutManager
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private inner class NotificationSwitchersListener :
        NotificationSwitchersAdapter.NotificationSwitchersListener {

        override fun onItemClicked(item: NotificationSwitchersModel) {
            viewModel.handleClick(item)
        }
    }
}