package com.projectx.householdtasks.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.projectx.householdtasks.databinding.CalendarItemBinding
import java.text.SimpleDateFormat

class CalendarListAdapter(private val onItemClicked: (Long) -> Unit) :
    ListAdapter<Pair<Long, Boolean>, CalendarListAdapter.CalendarHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarHolder {
        return CalendarHolder(
            CalendarItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            ),
            onItemClicked
        )
    }

    override fun onBindViewHolder(holder: CalendarHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class CalendarHolder(
        private var binding: CalendarItemBinding,
        private val onItemClicked: (Long) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(member: Pair<Long, Boolean>) {
            itemView.setOnClickListener {
                onItemClicked(member.first)
            }
            binding.apply {
                dayOfMonth.text = dateFormat.format(member.first)
                dayOfMontNumber.text = member.second.toString()
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Pair<Long, Boolean>>() {
            override fun areItemsTheSame(
                oldItem: Pair<Long, Boolean>,
                newItem: Pair<Long, Boolean>,
            ): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(
                oldItem: Pair<Long, Boolean>,
                newItem: Pair<Long, Boolean>,
            ): Boolean {
                return oldItem == newItem
            }
        }
        val dateFormat = SimpleDateFormat("d")
    }

}