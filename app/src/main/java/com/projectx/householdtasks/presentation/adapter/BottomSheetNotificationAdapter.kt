package com.projectx.householdtasks.presentation.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projectx.householdtasks.databinding.BottomSheetNotificationItemBinding
import com.projectx.householdtasks.presentation.viewmodel.NotificationSharedViewModel

class BottomSheetNotificationAdapter(
    private val intervals: List<String>,
    private val sharedViewModel: NotificationSharedViewModel
) : RecyclerView.Adapter<BottomSheetNotificationAdapter.IntervalViewHolder>() {

    inner class IntervalViewHolder(val binding: BottomSheetNotificationItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntervalViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = BottomSheetNotificationItemBinding.inflate(inflater, parent, false)
        return IntervalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IntervalViewHolder, position: Int) {
        val adapterPosition = holder.adapterPosition
        bind(holder, adapterPosition)
    }

    override fun getItemCount(): Int {
        return intervals.size
    }

    private fun bind(holder: IntervalViewHolder, adapterPosition: Int) {
        if (sharedViewModel.interval.value == intervals[adapterPosition]) {
            holder.binding.interval.isSelected = true
            holder.binding.interval.setTypeface(null, Typeface.BOLD)
        } else {
            holder.binding.interval.isSelected = false
            holder.binding.interval.setTypeface(null, Typeface.NORMAL)
        }
        holder.binding.interval.text = intervals[adapterPosition]
        holder.binding.interval.setOnClickListener {
            sharedViewModel.saveInterval(intervals[adapterPosition])
            sharedViewModel.setItemChecked(true)
        }
    }
}