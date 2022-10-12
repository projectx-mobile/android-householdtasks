package com.projectx.householdtasks.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.projectx.householdtasks.databinding.FamilyMemberItemBinding
import com.projectx.householdtasks.domain.model.FamilyMemberTest

class FamilyMembersListAdapter(private val onItemClicked: (FamilyMemberTest) -> Unit) :
    ListAdapter<FamilyMemberTest, FamilyMembersListAdapter.FamilyMembersHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FamilyMembersHolder {
        return FamilyMembersHolder(
            FamilyMemberItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            ),
            onItemClicked
        )
    }

    override fun onBindViewHolder(holder: FamilyMembersHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class FamilyMembersHolder(
        private var binding: FamilyMemberItemBinding,
        private val onItemClicked: (FamilyMemberTest) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(member: FamilyMemberTest) {
            itemView.setOnClickListener {
                onItemClicked(member)
            }
            binding.apply {
                name.text = member.name
                amountOfTasks.text =
                    "${member.completedTasks}/${member.tasks}"
                avatar.setProgress(((member.completedTasks.toFloat() / member.tasks) * 100).toInt())
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<FamilyMemberTest>() {
            override fun areItemsTheSame(
                oldItem: FamilyMemberTest,
                newItem: FamilyMemberTest,
            ): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(
                oldItem: FamilyMemberTest,
                newItem: FamilyMemberTest,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}