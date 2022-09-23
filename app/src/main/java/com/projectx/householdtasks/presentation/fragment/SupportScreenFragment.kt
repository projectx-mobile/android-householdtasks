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
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.faq.setOnClickListener {}

        binding.privacyPolicy.setOnClickListener {}

        binding.feedback.setOnClickListener {}
        binding.toolbarLayout.toolbar.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }
        setLink()
    }

    private fun setLink() {
        val spannableString = SpannableString(binding.helpLink.text)
        spannableString.setSpan(MyClickableSpan(), 31, 48, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.helpLink.text = spannableString
        binding.helpLink.movementMethod = LinkMovementMethod.getInstance()
    }

    inner class MyClickableSpan : ClickableSpan() {
        override fun onClick(widget: View) {
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