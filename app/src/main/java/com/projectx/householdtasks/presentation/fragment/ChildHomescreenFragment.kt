package com.projectx.householdtasks.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OVER_SCROLL_NEVER
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
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
            updateList()
            updateMonthTitle()
        }
    }

    private val datePicker by lazy {
        MaterialDatePicker.Builder.datePicker()
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
                materialDatePicker
            }
    }

    private val customTaskListAdapter = TaskListAdapter() {
    }

    private var calendarList = mutableListOf<Pair<Long, Boolean>>()
    private var firstDayInCalendar: Calendar = Calendar.getInstance()
    private val selectedDate = Calendar.getInstance()
    private val todaysDate = Calendar.getInstance()
    var notiAmo = 3

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = FragmentHomescreenChildBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    val notificationc by lazy {
        resources.getStringArray(R.array.notification_titles)
    }

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
        //Test while we don't have a backend
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

        collapsingToolbar.apply {
            setExpandedTitleTextAppearance(R.style.ExpandedAppBar)
            setCollapsedTitleTextAppearance(R.style.CollapsedAppBar)
        }
        val initToolbarHeight = collapsingToolbar.layoutParams.height
        collapsingToolbar.layoutParams =
            collapsingToolbar.layoutParams.apply { height = 0 }

        BottomSheetBehavior.from(coordinatorLayout).setBottomSheetCallback(
            object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(view: View, i: Int) {
                    when (i) {
                        BottomSheetBehavior.STATE_EXPANDED -> {
                            bottomSheetImageView.visibility = View.GONE
                        }
                        else -> {
                            bottomSheetImageView.visibility = View.VISIBLE
                        }
                    }
                }

                override fun onSlide(view: View, v: Float) {
                    val newHeight = (initToolbarHeight * v.absoluteValue).roundToInt()
                    collapsingToolbar.layoutParams =
                        collapsingToolbar.layoutParams.apply { height = newHeight }
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
                    datePicker.show(requireActivity().supportFragmentManager, "tag");
                    true
                }
                else -> false
            }
        }
        toolbar.setNavigationOnClickListener {
            BottomSheetBehavior.from(coordinatorLayout).state = BottomSheetBehavior.STATE_COLLAPSED
        }
        calendarIcon.setOnClickListener {
            datePicker.show(requireActivity().supportFragmentManager, "tag")
        }
        closeNotificationButton.setOnClickListener {
            notiAmo--
            when (notiAmo) {
                2 -> {
                    notificationLayout.background =
                        ResourcesCompat.getDrawable(resources,
                            R.drawable.notifications_section_stack_of_notifications,
                            null)
                    notificationTitle.text = notificationc[1]
                }
                1 -> {
                    notificationLayout.background =
                        ResourcesCompat.getDrawable(resources,
                            R.drawable.notifications_section_single_notification,
                            null)
                    notificationTitle.text = notificationc[1]
                }
                0 -> {
                    notificationLayout.background =
                        ResourcesCompat.getDrawable(resources,
                            R.drawable.notifications_section_no_new_reminders,
                            null)
                    notificationTitle.text = notificationc[2]
                }
                else -> {
                    notificationTitle.text = notificationc[2]
                }
            }
        }
    }

    private fun updateCalendar() {
        calendarList.clear()
        repeat(5) {
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
        val dateString =
            if (selectedDate.timeInMillis <= todaysDate.timeInMillis && selectedDate.timeInMillis + 86400000 > todaysDate.timeInMillis) resources.getString(
                R.string.active_tasks,
                5
            ) else resources.getString(
                R.string.active_tasks_format,
                selectedDate.get(Calendar.DAY_OF_MONTH),
                resources.getStringArray(R.array.month_names_current_tasks)[selectedDate.get(
                    Calendar.MONTH)],
                5
            )
        binding.apply {
            monthTitle.text = monthNames[selectedDate.get(Calendar.MONTH)]
            currentTasks.text = dateString
            collapsingToolbar.subtitle = dateString
        }
    }

    private fun updateList() {
        customCalendarListAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
