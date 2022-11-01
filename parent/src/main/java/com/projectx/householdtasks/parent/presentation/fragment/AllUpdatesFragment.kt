package com.projectx.householdtasks.parent.presentation.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.projectx.common.presentation.fragment.BaseFragment
import com.projectx.common.presentation.navigation.NavEvent
import com.projectx.householdtasks.parent.databinding.FragmentAllUpdatesBinding
import com.projectx.householdtasks.parent.presentation.adapter.UpdatesListAdapter
import com.projectx.householdtasks.parent.presentation.viewmodel.ParentHomescreenViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllUpdatesFragment :
    BaseFragment<FragmentAllUpdatesBinding, ParentHomescreenViewModel>(FragmentAllUpdatesBinding::inflate) {

    override val viewModel by viewModel<ParentHomescreenViewModel>()

    private val customUpdatesAdapter = UpdatesListAdapter {}

    override fun FragmentAllUpdatesBinding.bindUI() {
        toolbar.setNavigationOnClickListener {
            viewModel.navigate(NavEvent.Up)
        }
        allUpdatesRecyclerView.apply {
            adapter = customUpdatesAdapter
            layoutManager = LinearLayoutManager(requireContext())
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
        })
    }

    override fun ParentHomescreenViewModel.subscribeUI() {
        updatesTestList.observeUiState {
            customUpdatesAdapter.submitList(it)
        }
    }
}