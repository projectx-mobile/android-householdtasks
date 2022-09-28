package com.projectx.householdtasks.presentation

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.projectx.householdtasks.R
import com.projectx.householdtasks.databinding.LayoutBottomSheetBinding


class CustomBottomSheet : BottomSheetDialogFragment() {

    var bottomSheetBehavior: BottomSheetBehavior<*>? = null
    var bi: LayoutBottomSheetBinding? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        //inflating layout
        val view: View = LayoutBottomSheetBinding.inflate(layoutInflater).also {
            bi = it
        }.root


        //setting layout with bottom sheet
        bottomSheet.setContentView(view)

        bottomSheetBehavior = BottomSheetBehavior.from<View>(view.parent as View)


        //setting Peek at the 16:9 ratio keyline of its parent.
        bottomSheetBehavior?.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO


        //setting max height of bottom sheet
        bi?.root?.minimumHeight = Resources.getSystem().displayMetrics.heightPixels / 2
        //bi?.extraSpace.setMinimumHeight(Resources.getSystem().getDisplayMetrics().heightPixels / 2)
        bottomSheetBehavior?.setBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(view: View, i: Int) {
                if (BottomSheetBehavior.STATE_EXPANDED == i) {
                    showView(bi.appBarLayout, getActionBarSize())
                    hideAppBar(bi.profileLayout)
                }
                if (BottomSheetBehavior.STATE_COLLAPSED == i) {
                    hideAppBar(bi.appBarLayout)
                    showView(bi.profileLayout, getActionBarSize())
                }
                if (BottomSheetBehavior.STATE_HIDDEN == i) {
                    dismiss()
                }
            }

            override fun onSlide(view: View, v: Float) {}
        })

        //aap bar cancel button clicked
        bi.cancelBtn.setOnClickListener(object : View.OnClickListener() {
            override fun onClick(view: View?) {
                dismiss()
            }
        })

        //aap bar edit button clicked
        bi.editBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                Toast.makeText(context, "Edit button clicked", Toast.LENGTH_SHORT).show()
            }
        })

        //aap bar more button clicked
        bi.moreBtn.setOnClickListener(object : View.OnClickListener() {
            override fun onClick(view: View?) {
                Toast.makeText(context, "More button clicked", Toast.LENGTH_SHORT).show()
            }
        })


        //hiding app bar at the start
        hideAppBar(bi.appBarLayout)
        return bottomSheet
    }


    override fun onStart() {
        super.onStart()
        bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun hideAppBar(view: View) {
        val params = view.layoutParams
        params.height = 0
        view.layoutParams = params
    }

    private fun showView(view: View, size: Int) {
        val params = view.layoutParams
        params.height = size
        view.layoutParams = params
    }

    private fun getActionBarSize(): Int {
        val array =
            requireContext().theme.obtainStyledAttributes(intArrayOf(android.R.attr.actionBarSize))
        return array.getDimension(0, 0f).toInt()
    }

}