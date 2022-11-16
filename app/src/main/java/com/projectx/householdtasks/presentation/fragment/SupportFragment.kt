package com.projectx.householdtasks.presentation.fragment

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.projectx.householdtasks.R
import com.projectx.householdtasks.databinding.FragmentSupportBinding
import com.projectx.householdtasks.presentation.MainActivity
import com.projectx.householdtasks.presentation.adapter.SettingModel
import com.projectx.householdtasks.presentation.adapter.SettingsAdapter
import com.projectx.householdtasks.presentation.viewmodel.SupportViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SupportFragment : Fragment() {
    private var _binding: FragmentSupportBinding? = null
    private val binding get() = _binding!!

    private lateinit var settingsAdapter: SettingsAdapter

    private val viewModel by viewModel<SupportViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSupportBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).setBottomNavViewVisibility(false)
        binding.apply {
            settingsAdapter = SettingsAdapter(requireContext(), SettingListener())
            recyclerViewSettings.adapter = settingsAdapter
            settingsAdapter.submitList(viewModel.getSettingList())

            toolbarLayout.toolbar.setOnClickListener {
                findNavController().navigateUp()
            }
        }

        setLink()
    }

    private fun setLink() {
        val spannableString = SpannableString(binding.helpLink.text)
        spannableString.setSpan(
            HelpMessageClickableSpan(),
            START_PARENT_LINK,
            END_PARENT_LINK,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.helpLink.text = spannableString
        binding.helpLink.movementMethod = LinkMovementMethod.getInstance()
    }

    inner class HelpMessageClickableSpan : ClickableSpan() {
        override fun onClick(widget: View) {
//            TODO: add click
            Toast.makeText(requireContext(), "Link clicked", Toast.LENGTH_SHORT).show()
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.bgColor = ContextCompat.getColor(requireContext(), R.color.white)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private inner class SettingListener : SettingsAdapter.SettingListener {

        override fun onItemClicked(item: SettingModel) {
            viewModel.handleClick(item, findNavController())
        }
    }

    companion object {
        const val START_PARENT_LINK = 31
        const val END_PARENT_LINK = 48
    }
}