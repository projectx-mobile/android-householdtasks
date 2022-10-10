package com.projectx.householdtasks.presentation.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.projectx.householdtasks.R
import com.projectx.householdtasks.databinding.CalendarItemBinding
import java.text.SimpleDateFormat

class CalendarListAdapter(
    private val dayNames: Array<String>,
    private val onItemClicked: (Long) -> Unit,
) :
    ListAdapter<Pair<Long, Boolean>, CalendarListAdapter.CalendarHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarHolder {
        return CalendarHolder(
            CalendarItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            ),
            onItemClicked,
            dayNames
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: CalendarHolder, position: Int) {
        val current = getItem(position)

        holder.bind(current)
    }

    class CalendarHolder(
        private var binding: CalendarItemBinding,
        private val onItemClicked: (Long) -> Unit,
        private val dayNames: Array<String>,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.M)
        fun bind(member: Pair<Long, Boolean>) {
            itemView.setOnClickListener {
                onItemClicked(member.first)
            }
            binding.apply {
                dayNames[SimpleDateFormat("u").format(member.first).toInt() - 1].let {
                    dayOfMonth.text = it
                }
                dayOfMontNumber.text = dateFormat.format(member.first)
                if (member.second) {
                    binding.cardView.setCardBackgroundColor(binding.root.context.getColor(R.color.task_new))

                } else {
                    binding.cardView.setCardBackgroundColor(binding.root.context.getColor(R.color.primary_white_snow))

                }
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