package com.projectx.common.presentation.fragment

import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.projectx.common.R
import com.projectx.common.databinding.FragmentBottomSheetFaqBinding
import com.projectx.common.presentation.adapter.BottomSheetFaqAdapter
import com.projectx.common.presentation.model.Faq
import com.projectx.common.presentation.state.UiState.Companion.process
import com.projectx.common.presentation.viewmodel.SupportViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class BottomSheetFaqFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetFaqBinding? = null
    private val binding get() = _binding!!

    private var dialog: BottomSheetDialog? = null
    private val viewModel by viewModel<SupportViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetFaqBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBottomSheetHeight()
        setExitButtonClickListener()

        viewModel.faqList.observe(viewLifecycleOwner) {
            process(it) {
                setAdapter(it)
            }
        }

        addScrollListener()
    }

    private fun setBottomSheetHeight() {
        val bottomSheetBehavior: BottomSheetBehavior<View> =
            BottomSheetBehavior.from(view?.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        val layout: CoordinatorLayout = dialog?.findViewById(R.id.bottom_sheet) ?: return
        layout.minimumHeight = Resources.getSystem().displayMetrics.heightPixels
    }

    private fun setExitButtonClickListener() {
        binding.exitBottomSheet.setOnClickListener {
            dismiss()
        }
    }

    private fun setAdapter(faq: List<Faq>) {
        val adapter = BottomSheetFaqAdapter(faq)
        binding.recyclerViewFaqBottomSheet.adapter = adapter
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewFaqBottomSheet.layoutManager = layoutManager
    }

    private fun addScrollListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.recyclerViewFaqBottomSheet.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                if (binding.recyclerViewFaqBottomSheet.computeVerticalScrollOffset() != 0) {
                    binding.divider.visibility = View.VISIBLE
                } else {
                    binding.divider.visibility = View.INVISIBLE
                }
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): BottomSheetDialog {
        return super.onCreateDialog(savedInstanceState) as BottomSheetDialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}