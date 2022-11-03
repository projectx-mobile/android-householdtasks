package com.projectx.common.presentation.fragment

import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.projectx.common.R
import com.projectx.common.databinding.FragmentSupportBinding
import com.projectx.common.presentation.adapter.SettingsAdapter
import com.projectx.common.presentation.model.SettingModel
import com.projectx.common.presentation.viewmodel.SupportViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SupportFragment :
    BaseFragment<FragmentSupportBinding, SupportViewModel>(FragmentSupportBinding::inflate) {

    private val settingsAdapter by lazy { SettingsAdapter(requireContext(), SettingListener()) }

    override val viewModel by viewModel<SupportViewModel>()

    override fun FragmentSupportBinding.bindUI() {

        recyclerViewSettings.adapter = settingsAdapter

        toolbarLayout.toolbar.setOnClickListener {
            viewModel.onToolbarClick()
        }

        setLink()
    }

    override fun SupportViewModel.subscribeUI() {
        //TODO getSettingList
        settingsAdapter.submitList(getSettingList())
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
            viewModel.handleClick(item)
        }
    }

    companion object {
        const val START_PARENT_LINK = 31
        const val END_PARENT_LINK = 48
    }
}