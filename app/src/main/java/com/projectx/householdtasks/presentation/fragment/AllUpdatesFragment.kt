package com.projectx.householdtasks.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
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

        viewModel.getUpdates()?.also {
            customUpdatesAdapter.submitList(it)
            allUpdatesRecyclerView.apply {
                adapter = customUpdatesAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }

    }

    private fun FragmentAllUpdatesBinding.subscribeUI() = this.also {

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}