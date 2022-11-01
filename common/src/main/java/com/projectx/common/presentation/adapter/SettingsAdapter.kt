package com.projectx.common.presentation.adapter

import android.content.Context
import android.view.View
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.projectx.common.presentation.model.SettingModel
import com.projectx.common.databinding.ListItemSettingsBinding

class SettingsAdapter(
    private val context: Context,
    private val listener: SettingListener
) : ListAdapter<SettingModel, SettingsAdapter.ViewHolder>(SettingDiffCallback()) {

    private var _binding: ListItemSettingsBinding? = null
    private val binding get() = _binding!!

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        _binding = ListItemSettingsBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )

        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = getItem(position)
        binding.textviewSettingsItem.text = context.getString(item.title)
        binding.imageviewSettingsItem.background =
            AppCompatResources.getDrawable(context, item.icon)

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