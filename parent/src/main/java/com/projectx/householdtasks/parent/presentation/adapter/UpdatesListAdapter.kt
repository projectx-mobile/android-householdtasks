package com.projectx.householdtasks.parent.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.projectx.householdtasks.parent.databinding.UpdateItemBinding
import com.projectx.common.presentation.model.UpdatesTest

class UpdatesListAdapter(private val onItemClicked: (UpdatesTest) -> Unit) :
    ListAdapter<UpdatesTest, UpdatesListAdapter.UpdateHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpdateHolder {
        return UpdateHolder(
            UpdateItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            ),
            onItemClicked
        )
    }

    override fun onBindViewHolder(holder: UpdateHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class UpdateHolder(
        private var binding: UpdateItemBinding,
        private val onItemClicked: (UpdatesTest) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(update: UpdatesTest) {
            itemView.setOnClickListener {
                onItemClicked(update)
            }
            binding.apply {
                updateTitle.text = update.task
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<UpdatesTest>() {
            override fun areItemsTheSame(oldItem: UpdatesTest, newItem: UpdatesTest): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: UpdatesTest, newItem: UpdatesTest): Boolean {
                return oldItem == newItem
            }
        }
    }
}