package com.projectx.householdtasks.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.projectx.householdtasks.databinding.SettingSwitchBinding

class NotificationSwitchersAdapter(
    private val context: Context,
    private val listener: NotificationSwitchersListener
) :
    ListAdapter<NotificationSwitchersModel, NotificationSwitchersAdapter.ViewHolder>(
        NotificationSwitchersDiffCallback()
    ) {

    private var _binding: SettingSwitchBinding? = null
    private val binding get() = _binding!!

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        _binding =
            SettingSwitchBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: NotificationSwitchersAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        binding.title.text = context.getString(item.title)
        binding.image.background = AppCompatResources.getDrawable(context, item.icon)

        binding.root.setOnClickListener { listener.onItemClicked(item) }
    }

    interface NotificationSwitchersListener {
        fun onItemClicked(item: NotificationSwitchersModel)
    }

    private class NotificationSwitchersDiffCallback :
        DiffUtil.ItemCallback<NotificationSwitchersModel>() {
        override fun areItemsTheSame(
            oldItem: NotificationSwitchersModel,
            newItem: NotificationSwitchersModel
        ): Boolean =
            oldItem.title == newItem.title

        override fun areContentsTheSame(
            oldItem: NotificationSwitchersModel,
            newItem: NotificationSwitchersModel
        ): Boolean =
            oldItem == newItem
    }
}