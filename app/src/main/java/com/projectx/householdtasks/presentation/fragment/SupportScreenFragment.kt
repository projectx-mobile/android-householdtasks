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
import com.projectx.householdtasks.databinding.FragmentSupportScreenBinding

class SupportScreenFragment : Fragment() {
    private var _binding: FragmentSupportScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSupportScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            faq.root.setOnClickListener {
                val bottomSheetFragment = BottomSheetFaqFragment()
                bottomSheetFragment.show(childFragmentManager, "BottomSheet")
            }
            privacyPolicy.root.setOnClickListener {}
            toolbarLayout.toolbar.setOnClickListener {
                findNavController().navigateUp()
            }
        }

        setLink()
    }

    private fun setLink() {
        val spannableString = SpannableString(binding.helpLink.text)
        spannableString.setSpan(HelpMessageClickableSpan(), 31, 48, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
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
}