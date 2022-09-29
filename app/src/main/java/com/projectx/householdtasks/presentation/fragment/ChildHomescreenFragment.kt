package com.projectx.householdtasks.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.projectx.householdtasks.R
import com.projectx.householdtasks.databinding.FragmentHomescreenChildBinding
import com.projectx.householdtasks.presentation.adapters.CalendarListAdapter
import com.projectx.householdtasks.presentation.adapters.TaskListAdapter
import java.util.*

class ChildHomescreenFragment : BaseFragment() {

    private var _binding: FragmentHomescreenChildBinding? = null
    private val binding get() = _binding!!
    //private val viewModel by viewModel<ChildHomescreenViewModel>()

    private val customCalendarListAdapter = CalendarListAdapter() { clickedDate ->
        for (i in calendarList.indices) {
            if (calendarList[i].first == clickedDate) {
                calendarList[i] = Pair(calendarList[i].first, !calendarList[i].second)
            } else {
                calendarList[i] = Pair(calendarList[i].first, false)
            }
        }

    }
    private val customTaskListAdapter = TaskListAdapter() {

    }
    var calendarList = mutableListOf<Pair<Long, Boolean>>()
    val firstDayInCalendar = Calendar.getInstance()
    val chosenDate = firstDayInCalendar

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
        updateCalendar()
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
            2,
            2,
            3,
            1,
            2,
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
        BottomSheetBehavior.from(coordinatorLayout).setBottomSheetCallback(
            object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(view: View, i: Int) {
                    when (i) {
                        BottomSheetBehavior.STATE_EXPANDED -> {
                            imageView234.visibility = View.GONE
                            flexibleExampleAppbar.visibility = View.VISIBLE
                        }
                        BottomSheetBehavior.STATE_COLLAPSED -> {
                            imageView234.visibility = View.VISIBLE
                            flexibleExampleAppbar.visibility = View.GONE
                        }
                        BottomSheetBehavior.STATE_DRAGGING -> {
                            imageView234.visibility = View.VISIBLE
                            flexibleExampleAppbar.visibility = View.GONE
                        }
                        BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                            imageView234.visibility = View.VISIBLE
                            flexibleExampleAppbar.visibility = View.GONE
                        }
                        else -> {
                            imageView234.visibility = View.VISIBLE
                            flexibleExampleAppbar.visibility = View.GONE
                        }
                    }
                }

                override fun onSlide(view: View, v: Float) {}
            }
        )
    }


    private fun FragmentHomescreenChildBinding.subscribeUI() = this.also {

    }

    private fun updateCalendar() {
        calendarList.clear()
        repeat(5) {
            if(firstDayInCalendar.timeInMillis == chosenDate.timeInMillis) {
                calendarList.add(Pair(firstDayInCalendar.timeInMillis, true))
            } else {
                calendarList.add(Pair(firstDayInCalendar.timeInMillis, false))
            }
            firstDayInCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        customCalendarListAdapter.submitList(calendarList)
        customCalendarListAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}