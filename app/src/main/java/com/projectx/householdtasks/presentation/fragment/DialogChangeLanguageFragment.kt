package com.projectx.householdtasks.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.projectx.householdtasks.R
import com.projectx.householdtasks.databinding.DialogChangeLanguageBinding

class DialogChangeLanguageFragment : DialogFragment() {

    private var _binding: DialogChangeLanguageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogChangeLanguageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.setBackgroundDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.rectangle_white_without_borders
            )
        )
        binding.okButton.setOnClickListener {
            dismiss()
        }
        binding.negativeButton.setOnClickListener {
            dismiss()
        }
    }
}