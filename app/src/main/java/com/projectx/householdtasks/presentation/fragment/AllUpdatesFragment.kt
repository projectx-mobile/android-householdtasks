package com.projectx.householdtasks.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.projectx.householdtasks.databinding.FragmentAllUpdatesBinding
import com.projectx.householdtasks.presentation.UpdatesListAdapter
import com.projectx.householdtasks.presentation.viewmodel.ParentHomescreenViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class AllUpdatesFragment : BaseFragment() {

    private var _binding: FragmentAllUpdatesBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<ParentHomescreenViewModel>()

    private val customUpdatesAdapter = UpdatesListAdapter() {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = FragmentAllUpdatesBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bindUI().subscribeUI()
    }

    private fun FragmentAllUpdatesBinding.bindUI() = this.also {

        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        viewModel.getUpdates()?.also {
            customUpdatesAdapter.submitList(it)
            allUpdatesRecyclerView.apply {
                adapter = customUpdatesAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
        var appBarHeight: Int
        flexibleExampleAppbar.addOnOffsetChangedListener(OnOffsetChangedListener { appBarLayout, verticalOffset ->
            when (verticalOffset) {
                0 -> {
                    appBarHeight = flexibleExampleCollapsing.height
                    allUpdatesRecyclerView.translationY = appBarHeight.toFloat()
                }
                else -> {
                    appBarHeight = flexibleExampleCollapsing.height
                    allUpdatesRecyclerView.translationY = appBarHeight + verticalOffset.toFloat()
                }
            }
        }
        )
    }

    private fun FragmentAllUpdatesBinding.subscribeUI() = this.also {
        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}