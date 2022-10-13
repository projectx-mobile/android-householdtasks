package com.projectx.householdtasks.presentation.adapter

import android.content.Context
import android.view.View
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.projectx.householdtasks.databinding.ListItemSettingsBinding.inflate
import com.projectx.householdtasks.databinding.ListItemSettingsBinding

class SettingsAdapter(
    private val context: Context,
    private val listener: SettingListener
) :
    ListAdapter<SettingModel, SettingsAdapter.ViewHolder>(SettingDiffCallback()) {

    private var _binding: ListItemSettingsBinding? = null
    private val binding get() = _binding!!

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        _binding = LayoutInflater.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = getItem(position)
        binding.textviewSettingsItem.text = context.getString(item.title)
        Glide.with(binding.root).load(item.icon).circleCrop().into(binding.imageviewSettingsItem)

        binding.root.setOnClickListener { listener.onItemClicked(item) }
    }

    interface SettingListener {
        fun onItemClicked(item: SettingModel)
    }

    private class SettingDiffCallback : DiffUtil.ItemCallback<SettingModel>() {
        override fun areItemsTheSame(oldItem: SettingModel, newItem: SettingModel): Boolean =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: SettingModel, newItem: SettingModel): Boolean =
            oldItem == newItem
    }
}