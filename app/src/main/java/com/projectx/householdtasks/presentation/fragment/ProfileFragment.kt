package com.projectx.householdtasks.presentation.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.projectx.householdtasks.R
import com.projectx.householdtasks.databinding.FragmentProfileBinding
import com.projectx.householdtasks.presentation.adapter.MyFamilyProfileAdapter
import com.projectx.householdtasks.presentation.adapter.SettingModel
import com.projectx.householdtasks.presentation.adapter.SettingsAdapter
import com.projectx.householdtasks.presentation.viewmodel.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var settingsAdapter: SettingsAdapter
    private lateinit var otherSettingsAdapter: SettingsAdapter

    private val viewModel by viewModel<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        requireActivity().findViewById<CoordinatorLayout>(R.id.navigation).visibility = View.VISIBLE
        addScrollListener()

        binding.apply {
            settingsAdapter = SettingsAdapter(requireContext(), SettingListener())
            recyclerViewSettings.adapter = settingsAdapter
            settingsAdapter.submitList(viewModel.getSettingList())

            otherSettingsAdapter = SettingsAdapter(requireContext(), SettingListener())
            recyclerViewOtherSettings.adapter = otherSettingsAdapter
            otherSettingsAdapter.submitList(viewModel.getOtherSettingList())
        }
        setNavigation()

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

    private fun setNavigation() {
        binding.navigationBar.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }
    }

    private fun setAdapter() {
        val adapter = MyFamilyProfileAdapter(requireContext(), viewModel.getFamilyMembers())
        binding.recyclerViewFamilyMembers.adapter = adapter
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewFamilyMembers.layoutManager = layoutManager
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private inner class SettingListener : SettingsAdapter.SettingListener {

        override fun onItemClicked(item: SettingModel) {
            viewModel.handleClick(item, findNavController())
        }
    }
}

