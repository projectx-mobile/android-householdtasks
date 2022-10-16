package com.projectx.householdtasks.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.projectx.householdtasks.databinding.FragmentBottomSheetNotificationBinding
import com.projectx.householdtasks.presentation.adapter.BottomSheetNotificationAdapter
import com.projectx.householdtasks.presentation.viewmodel.NotificationSharedViewModel

class BottomSheetNotificationFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetNotificationBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: NotificationSharedViewModel by activityViewModels()

    private var adapter: BottomSheetNotificationAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setExitButtonClickListener()

        dismissDialogAfterIntervalClick()

        val intervals = createListOfIntervals()
        setAdapter(intervals)

        notifyIntervalOnChange()
    }

    private fun setExitButtonClickListener() {
        binding.exitBottomSheet.setOnClickListener {
            dismiss()
        }
    }

    private fun dismissDialogAfterIntervalClick() {
        sharedViewModel.isItemChecked.observe(viewLifecycleOwner) {
            if (it) {
                dismiss()
                sharedViewModel.setItemChecked(false)
            }
        }
    }

    private fun createListOfIntervals(): List<String> {
        return listOf("Каждые 2 часа", "За Х часов до дедлайна", "За Y часов до дедлайна")
    }

    private fun setAdapter(intervals: List<String>) {
        adapter = BottomSheetNotificationAdapter(intervals, sharedViewModel)
        binding.recyclerViewNotificationBottomSheet.adapter = adapter
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewNotificationBottomSheet.layoutManager = layoutManager
    }

    private fun notifyIntervalOnChange() {
        sharedViewModel.interval.observe(viewLifecycleOwner) {
            adapter?.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        _binding = null
        adapter = null
        super.onDestroyView()
    }
}