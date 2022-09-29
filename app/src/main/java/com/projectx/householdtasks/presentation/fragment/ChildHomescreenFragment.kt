package com.projectx.householdtasks.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.projectx.householdtasks.R
import com.projectx.householdtasks.databinding.FragmentHomescreenChildBinding
import com.projectx.householdtasks.presentation.CalendarListAdapter
import com.projectx.householdtasks.presentation.CustomBottomSheet
import com.projectx.householdtasks.presentation.TaskListAdapter

class ChildHomescreenFragment : BaseFragment() {

    private var _binding: FragmentHomescreenChildBinding? = null
    private val binding get() = _binding!!
    //private val viewModel by viewModel<ChildHomescreenViewModel>()

    private val customCalendarListAdapter = CalendarListAdapter() {

    }
    private val customTaskListAdapter = TaskListAdapter() {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = FragmentHomescreenChildBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomSheet = CustomBottomSheet()
        bottomSheet.show(parentFragmentManager, bottomSheet.tag)
        binding.bindUI().subscribeUI()
    }

    private fun FragmentHomescreenChildBinding.bindUI() = this.also {
        customCalendarListAdapter.submitList(listOf("1",
            "2",
            "3",
            "4",
            "5"))
        val asfsaf = LinearLayoutManager(requireContext())
        asfsaf.orientation = LinearLayoutManager.HORIZONTAL

        calendarRecyclerView.apply {
            adapter = customCalendarListAdapter
            layoutManager = asfsaf
        }

        customTaskListAdapter.submitList(listOf(1,
            2,
            3,
            1,
            2,
            3,
            1,
            2))

        taskListRecyclerView.apply {
            adapter = customTaskListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        taskListRecyclerView.addItemDecoration(
            object : androidx.recyclerview.widget.RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: android.graphics.Rect,
                    view: View,
                    parent: androidx.recyclerview.widget.RecyclerView,
                    state: androidx.recyclerview.widget.RecyclerView.State,
                ) {
                    super.getItemOffsets(outRect, view, parent, state)
                    outRect.bottom = resources.getDimensionPixelSize(R.dimen.spacing_normal)
                    outRect.left = resources.getDimensionPixelSize(R.dimen.spacing_normal)
                    outRect.right = resources.getDimensionPixelSize(R.dimen.spacing_normal)
                }
            })

        linearLayout
    }


    private fun FragmentHomescreenChildBinding.subscribeUI() = this.also {

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}