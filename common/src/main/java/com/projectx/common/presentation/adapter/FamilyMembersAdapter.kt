package com.projectx.common.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.projectx.common.R
import com.projectx.common.databinding.FamilyItemAccountStatusBinding
import com.projectx.common.presentation.model.FamilyMember

class FamilyMembersAdapter(
    private val context: Context
) : ListAdapter<FamilyMember, FamilyMembersAdapter.FamilyViewHolder>(DiffCallback) {
    inner class FamilyViewHolder(val binding: FamilyItemAccountStatusBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FamilyViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = FamilyItemAccountStatusBinding.inflate(inflater, parent, false)
        return FamilyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FamilyViewHolder, position: Int) {
        val adapterPosition = holder.adapterPosition
        bind(holder, adapterPosition)
    }

    private fun bind(holder: FamilyViewHolder, adapterPosition: Int) {
        val item = getItem(adapterPosition)
        holder.binding.apply {
            personName.text = item.name
            if (item.image == null) {
                personPhoto.background =
                    ContextCompat.getDrawable(context, R.drawable.oval_with_border)
                personPhoto.text = item.name.first().toString()
            } else {
                personPhoto.background = ContextCompat.getDrawable(context, item.image)
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<FamilyMember>() {
            override fun areItemsTheSame(oldItem: FamilyMember, newItem: FamilyMember) =
                oldItem === newItem

            override fun areContentsTheSame(oldItem: FamilyMember, newItem: FamilyMember) =
                oldItem == newItem
        }
    }
}