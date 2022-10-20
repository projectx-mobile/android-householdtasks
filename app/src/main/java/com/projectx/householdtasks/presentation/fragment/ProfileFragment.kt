package com.projectx.householdtasks.presentation.fragment

import android.os.Build
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.projectx.householdtasks.databinding.FragmentProfileBinding
import com.projectx.householdtasks.presentation.adapter.MyFamilyProfileAdapter
import com.projectx.householdtasks.presentation.adapter.SettingModel
import com.projectx.householdtasks.presentation.adapter.SettingsAdapter
import com.projectx.householdtasks.presentation.viewmodel.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment :
    BaseFragment<FragmentProfileBinding, ProfileViewModel>(FragmentProfileBinding::inflate) {

    override val viewModel by viewModel<ProfileViewModel>()

    private val settingsAdapter by lazy { SettingsAdapter(requireContext(), SettingListener()) }
    private val otherSettingsAdapter by lazy { SettingsAdapter(requireContext(), SettingListener()) }

    override fun FragmentProfileBinding.bindUI() {
        addScrollListener()
        setNavigation()
        setupAdapters()
    }

    override fun ProfileViewModel.subscribeUI() {
        familyMembers.observeUiState {
            binding.recyclerViewFamilyMembers.adapter = MyFamilyProfileAdapter(requireContext(), it)
        }
        settingList.observeUiState {
            settingsAdapter.submitList(it)
        }
        otherSettingList.observeUiState {
            otherSettingsAdapter.submitList(it)
        }
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
            viewModel.navigateToEditProfile(findNavController())
        }
    }

    private fun FragmentProfileBinding.setupAdapters() {
        recyclerViewFamilyMembers.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        recyclerViewSettings.adapter = settingsAdapter
        recyclerViewOtherSettings.adapter = otherSettingsAdapter
    }

    private inner class SettingListener : SettingsAdapter.SettingListener {

        override fun onItemClicked(item: SettingModel) {
            viewModel.handleClick(item, findNavController())
        }
    }
}

