package com.projectx.common.presentation.fragment

import android.os.Build
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.projectx.common.databinding.FragmentProfileBinding
import com.projectx.common.presentation.adapter.MyFamilyProfileAdapter
import com.projectx.common.presentation.adapter.SettingsAdapter
import com.projectx.common.presentation.model.SettingModel
import com.projectx.common.presentation.viewmodel.ProfileViewModel
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
            binding.recyclerViewFamilyMembers.adapter = MyFamilyProfileAdapter(requireContext(), it) {
                viewModel.onPersonPhotoClick()
            }
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
            viewModel.onNavigationBarClick()
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
            viewModel.handleClick(item)
        }
    }
}

