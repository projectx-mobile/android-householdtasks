package com.projectx.householdtasks.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.projectx.householdtasks.R
import com.projectx.householdtasks.databinding.FamilyItemAccountStatusBinding
import com.projectx.householdtasks.presentation.FamilyMember

class FamilyMembersAdapter(
    private val context: Context,
    private val familyMembers: List<FamilyMember>
) : RecyclerView.Adapter<FamilyMembersAdapter.FamilyViewHolder>() {
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

    override fun getItemCount(): Int {
        return familyMembers.size
    }

    private fun bind(holder: FamilyViewHolder, adapterPosition: Int) {
        holder.binding.personName.text = familyMembers[adapterPosition].name
        if (familyMembers[adapterPosition].image == null) {
            holder.binding.personPhoto.background =
                ContextCompat.getDrawable(context, R.drawable.oval_with_boarder)
            holder.binding.personPhoto.text = familyMembers[adapterPosition].name.first().toString()
        } else {
            holder.binding.personPhoto.background =
                ContextCompat.getDrawable(context, familyMembers[adapterPosition].image!!)
        }
    }
}