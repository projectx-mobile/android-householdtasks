package com.projectx.householdtasks.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OVER_SCROLL_NEVER
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.datepicker.MaterialDatePicker
import com.projectx.householdtasks.R
import com.projectx.householdtasks.databinding.FragmentHomescreenChildBinding
import com.projectx.householdtasks.presentation.adapters.CalendarListAdapter
import com.projectx.householdtasks.presentation.adapters.TaskListAdapter
import java.util.*
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

class ChildHomescreenFragment : BaseFragment() {

    private var _binding: FragmentHomescreenChildBinding? = null
    private val binding get() = _binding!!

    //private val viewModel by viewModel<ChildHomescreenViewModel>()
    private val weekDaysNames: Array<String> by lazy {
        resources.getStringArray(R.array.week_days)
    }
    private val monthNames: Array<String> by lazy {
        resources.getStringArray(R.array.month_names)
    }

    private val customCalendarListAdapter by lazy {
        CalendarListAdapter(weekDaysNames) { clickedDate ->
            calendarList.forEachIndexed { index, pair ->
                calendarList[index] = with(pair) {
                    when (first) {
                        clickedDate -> {
                            selectedDate.timeInMillis = clickedDate
                            Pair(first, true)

                        }
                        else -> Pair(first, false)
                    }
                }
            }
            fd()
            updateMonthTitle()
        }
    }

    fun fd() {
        customCalendarListAdapter.notifyDataSetChanged()
    }

    private val customTaskListAdapter = TaskListAdapter() {
    }

    private var calendarList = mutableListOf<Pair<Long, Boolean>>()
    private var firstDayInCalendar: Calendar = Calendar.getInstance()
    private val selectedDate = Calendar.getInstance()
    private val todaysDate = Calendar.getInstance()

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
        toolbar.inflateMenu(R.menu.date_chooser)
        calendarRecyclerView.apply {
            adapter = customCalendarListAdapter
            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
        }

        customTaskListAdapter.submitList(List(16) { (1..3).random() })

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

        flexibleExampleCollapsing.apply {
            setExpandedTitleTextAppearance(R.style.ExpandedAppBar)
            setCollapsedTitleTextAppearance(R.style.CollapsedAppBar)
        }
        val initToolbarHeight = flexibleExampleCollapsing.layoutParams.height
        flexibleExampleCollapsing.layoutParams =
            flexibleExampleCollapsing.layoutParams.apply { height = 0 }

        BottomSheetBehavior.from(coordinatorLayout).setBottomSheetCallback(
            object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(view: View, i: Int) {
                    when (i) {
                        BottomSheetBehavior.STATE_EXPANDED -> {
                            imageView234.visibility = View.GONE
                        }
                        else -> {
                            imageView234.visibility = View.VISIBLE
                        }
                    }
                }

                override fun onSlide(view: View, v: Float) {
                    val newHeight = (initToolbarHeight * v.absoluteValue).roundToInt()
                    flexibleExampleCollapsing.layoutParams =
                        flexibleExampleCollapsing.layoutParams.apply { height = newHeight }
                }
            }
        )

        taskListRecyclerView.overScrollMode = OVER_SCROLL_NEVER
        calendarRecyclerView.overScrollMode = OVER_SCROLL_NEVER
    }

    private fun FragmentHomescreenChildBinding.subscribeUI() = this.also {
        leftCalendar.setOnClickListener {
            firstDayInCalendar.add(Calendar.DAY_OF_MONTH, -1)
            updateCalendar()
        }
        rightCalendar.setOnClickListener {
            firstDayInCalendar.add(Calendar.DAY_OF_MONTH, 1)
            updateCalendar()
        }

        selectedDateTitle.setOnClickListener {
            firstDayInCalendar.timeInMillis = todaysDate.timeInMillis
            updateCalendar()
        }

        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_choose_date -> {
                    val datePicker =
                        MaterialDatePicker.Builder.datePicker()
                            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                            .setTitleText("Select date")
                            .setTheme(R.style.ThemeOverlay_App_DatePicker)
                            .build()
                            .show(requireActivity().supportFragmentManager, "tag");
                    true
                }
                else -> false
            }
        }
        calendarIcon.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setTitleText("Select date")
                .setTheme(R.style.ThemeOverlay_App_DatePicker)
                .build()
                .let { materialDatePicker ->
                    materialDatePicker.addOnPositiveButtonClickListener {
                        firstDayInCalendar.timeInMillis = it
                        selectedDate.timeInMillis = it
                        updateCalendar()
                    }
                    materialDatePicker.show(requireActivity().supportFragmentManager, "tdsfag")
                }
        }
    }

    private fun updateCalendar() {
        calendarList.clear()
        repeat(5) {
            Log.d("Calendar", "first: ${firstDayInCalendar.time} ---- second: ${selectedDate.time}")
            if (firstDayInCalendar.timeInMillis == selectedDate.timeInMillis) {
                calendarList.add(Pair(firstDayInCalendar.timeInMillis, true))
            } else {
                calendarList.add(Pair(firstDayInCalendar.timeInMillis, false))
            }
            firstDayInCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        firstDayInCalendar.add(Calendar.DAY_OF_MONTH, -5)
        customCalendarListAdapter.submitList(calendarList)
        customCalendarListAdapter.notifyDataSetChanged()

        updateMonthTitle()
    }

    private fun updateMonthTitle() {
        binding.monthTitle.text = monthNames[selectedDate.get(Calendar.MONTH)]
        binding.currentTasks.text = resources.getString(
            R.string.active_tasks_format,
            selectedDate.get(Calendar.DAY_OF_MONTH),
            resources.getStringArray(R.array.month_names_current_tasks)[selectedDate.get(Calendar.MONTH)],
            5
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}