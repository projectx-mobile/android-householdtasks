package com.projectx.householdtasks.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projectx.householdtasks.databinding.FamilyItemAccountStatusBinding
import com.projectx.householdtasks.presentation.FamilyMember

class FamilyMembersAdapter(private val familyMembers: List<FamilyMember>): RecyclerView.Adapter<FamilyMembersAdapter.FamilyViewHolder>() {
    inner class FamilyViewHolder(val binding: FamilyItemAccountStatusBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FamilyViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = FamilyItemAccountStatusBinding.inflate(inflater, parent, false)
        return FamilyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FamilyViewHolder, position: Int) {
        val adapterPosition = holder.adapterPosition
        bind(holder, adapterPosition)
    }

    override fun getItemCount(): Int {
        return familyMembers.size
    }

    private fun bind(holder: FamilyViewHolder, adapterPosition: Int) {
        holder.binding.personName.text = familyMembers[adapterPosition].name
        if (familyMembers[adapterPosition].firstLetter != null) {
            holder.binding.personPhoto.text = familyMembers[adapterPosition].firstLetter
        }
        holder.binding.personPhoto.background = familyMembers[adapterPosition].image
    }
}