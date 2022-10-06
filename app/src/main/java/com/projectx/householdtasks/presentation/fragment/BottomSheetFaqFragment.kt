package com.projectx.householdtasks.presentation.fragment

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.projectx.householdtasks.R
import com.projectx.householdtasks.databinding.FragmentBottomSheetFaqBinding
import com.projectx.householdtasks.presentation.BottomSheetFaqAdapter
import com.projectx.householdtasks.presentation.Faq


class BottomSheetFaqFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetFaqBinding? = null
    private val binding get() = _binding!!
    private var dialog: BottomSheetDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetFaqBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomSheetBehavior: BottomSheetBehavior<View> = BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        val layout: CoordinatorLayout = dialog?.findViewById(R.id.bottom_sheet) ?: return
        layout.minimumHeight = Resources.getSystem().displayMetrics.heightPixels
        binding.exitBottomSheet.setOnClickListener {
            dismiss()
        }
        val bottomSheet = view.parent as View

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
            ),
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
            ),
            Faq(
                "Вопрос четвертый",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
            ),
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
        addScrollListener(layoutManager)
        binding.recyclerViewFaqBottomSheet.layoutManager = layoutManager
    }

    private fun addScrollListener(layoutManager: LinearLayoutManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.recyclerViewFaqBottomSheet.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                Log.i("!!!!!!YYY", binding.recyclerViewFaqBottomSheet.computeVerticalScrollOffset().toString())
                if (binding.recyclerViewFaqBottomSheet.computeVerticalScrollOffset() != 0) {
                    binding.divider.visibility = View.VISIBLE
                } else {
                    binding.divider.visibility = View.INVISIBLE
                }
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): BottomSheetDialog {
         dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        return dialog as BottomSheetDialog
    }


//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val dialog = super.onCreateDialog(savedInstanceState)
//        dialog.setOnShowListener { dialogInterface ->
//            val bottomSheetDialog = dialogInterface as BottomSheetDialog
//            setupFullHeight(bottomSheetDialog)
//        }
//        return dialog
//    }
//
//    private fun setupFullHeight(bottomSheetDialog: BottomSheetDialog) {
//        val bottomSheet =
//            bottomSheetDialog.findViewById<View>(R.id.bottom_sheet) as FrameLayout?
//        val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet as FrameLayout)
//        val layoutParams = bottomSheet.layoutParams
//        val windowHeight = getWindowHeight()
//        if (layoutParams != null) {
//            layoutParams.height = windowHeight
//        }
//        bottomSheet.layoutParams = layoutParams
//        behavior.state = BottomSheetBehavior.STATE_EXPANDED
//    }
//
//    private fun getWindowHeight(): Int {
//        // Calculate window height for fullscreen use
//        val displayMetrics = DisplayMetrics()
//        (context as Activity?)!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
//        return displayMetrics.heightPixels
//    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}