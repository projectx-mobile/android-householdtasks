package com.projectx.householdtasks.presentation.fragment

import android.os.Build
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.projectx.householdtasks.databinding.FragmentNotificationBinding
import com.projectx.householdtasks.presentation.adapter.FamilyMembersAdapter
import com.projectx.householdtasks.presentation.adapter.NotificationSwitchersAdapter
import com.projectx.householdtasks.presentation.adapter.NotificationSwitchersModel
import com.projectx.householdtasks.presentation.event.NotificationScreenEvent
import com.projectx.householdtasks.presentation.viewmodel.NotificationSharedViewModel
import com.projectx.householdtasks.presentation.viewmodel.NotificationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationFragment :
    BaseFragment<FragmentNotificationBinding, NotificationSharedViewModel.NotificationScreenUiState, NotificationScreenEvent>() {

    override fun getViewBinding() = FragmentNotificationBinding.inflate(layoutInflater)

    override fun getBaseViewModel() = viewModel<NotificationViewModel>().value

    private var bottomSheetFragment: BottomSheetNotificationFragment? = null

    private val sharedViewModel by activityViewModels<NotificationSharedViewModel>()

    private lateinit var switchesListAdapter: NotificationSwitchersAdapter

    override fun bindUI() = super.bindUI().apply {
        switchesListAdapter =
            NotificationSwitchersAdapter(requireContext(), NotificationSwitchersListener())
        recyclerViewSwitches.adapter = switchesListAdapter

        toolbarLayout.toolbar.setOnClickListener {
            viewModel.onEvent(NotificationScreenEvent.NavBack(findNavController()))
        }
        addScrollListener()

        bottomSheetFragment = BottomSheetNotificationFragment()
        notificationPicker.setOnClickListener {
            bottomSheetFragment?.show(childFragmentManager, "BottomSheet")
        }

        recyclerViewNotification.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun FragmentNotificationBinding.addScrollListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            nestedScrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                if (scrollY > 0) {
                    appBarLayout.visibility = View.VISIBLE
                } else {
                    appBarLayout.visibility = View.INVISIBLE
                }
            }
        }
    }

    override fun subscribeUI(isDistinctUntilChangedEnabled: Boolean) =
        super.subscribeUI(isDistinctUntilChangedEnabled).apply {
            sharedViewModel.interval.observe(viewLifecycleOwner) { interval ->
                textViewInterval.text = interval
            }
        }

    override fun FragmentNotificationBinding.processState(state: NotificationSharedViewModel.NotificationScreenUiState) {
        switchesListAdapter.submitList(state.switchersList)
        recyclerViewNotification.adapter = FamilyMembersAdapter(requireContext(), state.familyList)
    }

    private inner class NotificationSwitchersListener :
        NotificationSwitchersAdapter.NotificationSwitchersListener {

        override fun onItemClicked(item: NotificationSwitchersModel) {
            viewModel.onEvent(NotificationScreenEvent.OnItemClicked(item))
        }
    }
}