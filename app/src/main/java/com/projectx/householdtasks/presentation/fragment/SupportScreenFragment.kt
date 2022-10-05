package com.projectx.householdtasks.presentation.fragment

import android.content.res.Resources
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
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
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
        binding.faq.setOnClickListener {
//            val bottomSheetFragment = BottomSheetDialog(requireContext())
            val bottomSheetFragment = BottomSheetFaqFragment()
//            val bottomSheetView = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_bottom_sheet_faq, null)
//            bottomSheetFragment.setContentView(bottomSheetView)
//            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView.parent as View)
//            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
//            val layout: CoordinatorLayout? = bottomSheetFragment.findViewById(R.id.bottom_sheet)
//            if (layout != null) {
//                layout.minimumHeight = Resources.getSystem().displayMetrics.heightPixels
//            }

//            bottomSheetFragment.show()
            bottomSheetFragment.show(childFragmentManager, "BottomSheet")
        }

        binding.privacyPolicy.setOnClickListener {}

        binding.feedback.setOnClickListener {}
        binding.toolbarLayout.toolbar.setOnClickListener {
            findNavController().navigateUp()
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