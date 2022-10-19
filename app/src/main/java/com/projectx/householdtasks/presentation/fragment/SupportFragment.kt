package com.projectx.householdtasks.presentation.fragment

import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.projectx.householdtasks.R
import com.projectx.householdtasks.databinding.FragmentSupportBinding
import com.projectx.householdtasks.presentation.adapter.SettingModel
import com.projectx.householdtasks.presentation.adapter.SettingsAdapter
import com.projectx.householdtasks.presentation.event.SupportScreenEvent
import com.projectx.householdtasks.presentation.viewmodel.SupportViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SupportFragment :
    BaseFragment<FragmentSupportBinding, List<SettingModel>, SupportScreenEvent>() {

    override fun getViewBinding() = FragmentSupportBinding.inflate(layoutInflater)

    override fun getBaseViewModel() = viewModel<SupportViewModel>().value

    private lateinit var settingsAdapter: SettingsAdapter

    override fun bindUI() = super.bindUI().apply {
        settingsAdapter = SettingsAdapter(requireContext(), SettingListener())
        recyclerViewSettings.adapter = settingsAdapter

        toolbarLayout.toolbar.setOnClickListener {
            viewModel.onEvent(SupportScreenEvent.NavBack(findNavController()))
        }

        setLink()
    }

    override fun FragmentSupportBinding.processState(state: List<SettingModel>) {
        settingsAdapter.submitList(state)
    }

    private fun FragmentSupportBinding.setLink() {
        val spannableString = SpannableString(helpLink.text)
        spannableString.setSpan(
            HelpMessageClickableSpan(),
            START_PARENT_LINK,
            END_PARENT_LINK,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        helpLink.text = spannableString
        helpLink.movementMethod = LinkMovementMethod.getInstance()
    }

    inner class HelpMessageClickableSpan : ClickableSpan() {
        override fun onClick(widget: View) {
            //TODO: add click
            Toast.makeText(requireContext(), "Link clicked", Toast.LENGTH_SHORT).show()
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.bgColor = ContextCompat.getColor(requireContext(), R.color.white)
        }
    }

    private inner class SettingListener : SettingsAdapter.SettingListener {

        override fun onItemClicked(item: SettingModel) {
            viewModel.onEvent(SupportScreenEvent.OnItemClicked(item, findNavController()))
        }
    }

    companion object {
        const val START_PARENT_LINK = 31
        const val END_PARENT_LINK = 48
    }
}