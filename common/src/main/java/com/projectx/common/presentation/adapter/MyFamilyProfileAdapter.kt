package com.projectx.common.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.projectx.common.presentation.model.FamilyMember
import com.projectx.common.R
import com.projectx.common.databinding.FamilyItemProfileBinding

class MyFamilyProfileAdapter(
    private val context: Context,
    private val familyMembers: List<FamilyMember>,
    private val onPersonPhotoClick: () -> Unit
) : RecyclerView.Adapter<MyFamilyProfileAdapter.FamilyViewHolder>() {
    inner class FamilyViewHolder(val binding: FamilyItemProfileBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FamilyViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = FamilyItemProfileBinding.inflate(inflater, parent, false)
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
                ContextCompat.getDrawable(context, R.drawable.oval_with_border)
            holder.binding.personPhoto.text = familyMembers[adapterPosition].name.first().toString()
        } else {
            holder.binding.personPhoto.background =
                ContextCompat.getDrawable(context, familyMembers[adapterPosition].image!!)
        }
        if (adapterPosition == 0) {
            holder.binding.personPhoto.setOnClickListener {
                onPersonPhotoClick()
            }
        }
    }
}