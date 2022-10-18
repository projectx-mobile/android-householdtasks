package com.projectx.householdtasks.presentation.fragment

import android.os.Build
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.projectx.householdtasks.databinding.FragmentProfileBinding
import com.projectx.householdtasks.presentation.adapter.MyFamilyProfileAdapter
import com.projectx.householdtasks.presentation.adapter.SettingModel
import com.projectx.householdtasks.presentation.adapter.SettingsAdapter
import com.projectx.householdtasks.presentation.event.ProfileScreenEvent
import com.projectx.householdtasks.presentation.viewmodel.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment :
    BaseFragment<FragmentProfileBinding, ProfileViewModel.ProfileViewUiState, ProfileScreenEvent>() {

    override fun getViewBinding() = FragmentProfileBinding.inflate(layoutInflater)

    override fun getBaseViewModel() = viewModel<ProfileViewModel>().value

    private lateinit var settingsAdapter: SettingsAdapter
    private lateinit var otherSettingsAdapter: SettingsAdapter

    override fun bindUI() = super.bindUI().apply {
        //requireActivity().findViewById<CoordinatorLayout>(R.id.navigation).visibility = View.VISIBLE
        addScrollListener()
        setNavigation()
        setupAdapters()
    }

    override fun FragmentProfileBinding.processState(state: ProfileViewModel.ProfileViewUiState) {
        recyclerViewFamilyMembers.adapter =
            MyFamilyProfileAdapter(requireContext(), state.familyMembers)

        settingsAdapter.submitList(state.settingList)
        otherSettingsAdapter.submitList(state.otherSettingList)
    }

    private fun FragmentProfileBinding.addScrollListener() {
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

    private fun FragmentProfileBinding.setNavigation() {
        navigationBar.setOnClickListener {
            viewModel.onEvent(ProfileScreenEvent.NavigateToEditProfile(findNavController()))
        }
    }

    private fun FragmentProfileBinding.setupAdapters() {
        recyclerViewFamilyMembers.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        settingsAdapter = SettingsAdapter(requireContext(), SettingListener())
        recyclerViewSettings.adapter = settingsAdapter

        otherSettingsAdapter = SettingsAdapter(requireContext(), SettingListener())
        recyclerViewOtherSettings.adapter = otherSettingsAdapter
    }

    private inner class SettingListener : SettingsAdapter.SettingListener {

        override fun onItemClicked(item: SettingModel) {
            viewModel.onEvent(ProfileScreenEvent.OnItemClicked(item, findNavController()))
        }
    }
}

