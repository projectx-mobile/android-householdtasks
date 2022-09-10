package com.projectx.householdtasks.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputLayout
import com.projectx.householdtasks.R

class CustomTextInputLayout(context: Context, attributeSet: AttributeSet) :
    TextInputLayout(context, attributeSet) {
    init {
        this.addOnEditTextAttachedListener {
            this.editText!!.setOnFocusChangeListener { v, hasFocus ->
                updateStyles()
            }
        }
        updateStyles()

    }

    override fun setError(error: CharSequence?) {
        super.setError(error)
        val iconView =
            this.findViewById<View>(com.google.android.material.R.id.text_input_error_icon)
        iconView.setPadding(0, 46, 0, 0)

        updateStyles()
    }

    override fun setErrorEnabled(enabled: Boolean) {
        super.setErrorEnabled(enabled)
        updateStyles()
    }

    private fun updateStyles() {
        if (editText == null) return
        if (this.isErrorEnabled && this.error == " ") {
            this.setPadding(0, 0, 0, -62)
        } else {
            this.setPadding(0, 0, 0, 0)
        }
        if (editText!!.isFocused) {
            if (error != null) {
                editText!!.background =
                    ContextCompat.getDrawable(context, R.drawable.error_in_focus)
            } else {
                editText!!.background =
                    ContextCompat.getDrawable(context, R.drawable.text_input_layout)
            }
        } else {
            if (error != null) {
                val xx = ContextCompat.getDrawable(context, R.drawable.rectangle_error)
                editText!!.background = xx
            } else {
                editText!!.background =
                    ContextCompat.getDrawable(context, R.drawable.text_input_layout)
            }
        }
    }
}
