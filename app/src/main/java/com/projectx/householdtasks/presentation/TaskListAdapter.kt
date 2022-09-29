package com.projectx.householdtasks.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.projectx.householdtasks.databinding.TaskCardBinding
import com.projectx.householdtasks.presentation.support.setProgress

class TaskListAdapter(private val onItemClicked: (Int) -> Unit) :
    ListAdapter<Int, TaskListAdapter.TaskHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        return TaskHolder(
            TaskCardBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            ),
            onItemClicked
        )
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class TaskHolder(
        private var binding: TaskCardBinding,
        private val onItemClicked: (Int) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(member: Int) {
            itemView.setOnClickListener {
                onItemClicked(member)
            }
            binding.apply {
                taskCard.setProgress(member)
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Int>() {
            override fun areItemsTheSame(
                oldItem: Int,
                newItem: Int,
            ): Boolean {
                return oldItem == newItem + 1
            }

            override fun areContentsTheSame(
                oldItem: Int,
                newItem: Int,
            ): Boolean {
                return oldItem == newItem + 1
            }
        }
    }
}