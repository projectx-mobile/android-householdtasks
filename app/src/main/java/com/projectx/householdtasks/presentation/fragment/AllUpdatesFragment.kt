package com.projectx.householdtasks.presentation.fragment

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.projectx.householdtasks.databinding.FragmentAllUpdatesBinding
import com.projectx.householdtasks.presentation.adapter.UpdatesListAdapter
import com.projectx.householdtasks.presentation.event.ParentHomeScreenEvent
import com.projectx.householdtasks.presentation.viewmodel.ParentHomescreenViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllUpdatesFragment :
    BaseFragment<FragmentAllUpdatesBinding, ParentHomescreenViewModel.ParentHomeScreenUiState, ParentHomeScreenEvent>() {

    override fun getViewBinding() = FragmentAllUpdatesBinding.inflate(layoutInflater)

    override fun getBaseViewModel() = viewModel<ParentHomescreenViewModel>().value

    private val customUpdatesAdapter by lazy { UpdatesListAdapter {} }

    override fun bindUI() = super.bindUI().apply {
        var appBarHeight: Int
        flexibleExampleAppbar.addOnOffsetChangedListener(
            OnOffsetChangedListener { _, verticalOffset ->
                when (verticalOffset) {
                    0 -> {
                        appBarHeight = flexibleExampleCollapsing.height
                        allUpdatesRecyclerView.translationY = appBarHeight.toFloat()
                    }
                    else -> {
                        appBarHeight = flexibleExampleCollapsing.height
                        allUpdatesRecyclerView.translationY =
                            appBarHeight + verticalOffset.toFloat()
                    }
                }
            }
        )
        allUpdatesRecyclerView.apply {
            adapter = customUpdatesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        toolbar.setNavigationOnClickListener {
            viewModel.onEvent(ParentHomeScreenEvent.NavBack(findNavController()))
        }
    }

    override fun FragmentAllUpdatesBinding.processState(state: ParentHomescreenViewModel.ParentHomeScreenUiState) {
        customUpdatesAdapter.submitList(state.updates)
    }
}