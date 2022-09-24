package com.projectx.householdtasks.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.projectx.householdtasks.databinding.CalendarItemBinding

class CalendarListAdapter(private val onItemClicked: (String) -> Unit) :
    ListAdapter<String, CalendarListAdapter.CalendarHolder>(DiffCallback) {

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
        private val onItemClicked: (String) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(member: String) {
            itemView.setOnClickListener {
                onItemClicked(member)
            }
            binding.apply {
                dayOfMonth.text = member
                dayOfMontNumber.text = member
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(
                oldItem: String,
                newItem: String,
            ): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(
                oldItem: String,
                newItem: String,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}