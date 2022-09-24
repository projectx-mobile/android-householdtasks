package com.projectx.householdtasks.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.projectx.householdtasks.databinding.FragmentHomescreenChildBinding
import com.projectx.householdtasks.presentation.CalendarListAdapter
import okhttp3.internal.threadFactory

class ChildHomescreenFragment : BaseFragment() {

    private var _binding: FragmentHomescreenChildBinding? = null
    private val binding get() = _binding!!
    //private val viewModel by viewModel<ChildHomescreenViewModel>()

    private val customCalendarListAdapterAdapter = CalendarListAdapter() {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = FragmentHomescreenChildBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bindUI().subscribeUI()
    }

    private fun FragmentHomescreenChildBinding.bindUI() = this.also {
        customCalendarListAdapterAdapter.submitList(listOf("1",
            "2",
            "3",
            "4",
            "5"))
        val asfsaf = LinearLayoutManager(requireContext())
        asfsaf.orientation = LinearLayoutManager.HORIZONTAL

        calendarRecyclerView.adapter = customCalendarListAdapterAdapter
        calendarRecyclerView.layoutManager = asfsaf

    }


    private fun FragmentHomescreenChildBinding.subscribeUI() = this.also {

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}