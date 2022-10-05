package com.projectx.householdtasks.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.projectx.householdtasks.databinding.FragmentBottomSheetFaqBinding
import com.projectx.householdtasks.presentation.BottomSheetFaqAdapter
import com.projectx.householdtasks.presentation.Faq


class BottomSheetFaqFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetFaqBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetFaqBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.exitBottomSheet.setOnClickListener {
            dismiss()
        }

        val faq = listOf(
            Faq(
                "Вопрос первый",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
            ),
            Faq(
                "Вопрос второй",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
            ),
            Faq(
                "Вопрос третий",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
            ),
            Faq(
                "Вопрос четвертый",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
            )
        )

        val adapter = BottomSheetFaqAdapter(faq)
        binding.recyclerViewFaqBottomSheet.adapter = adapter
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewFaqBottomSheet.layoutManager = layoutManager
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}