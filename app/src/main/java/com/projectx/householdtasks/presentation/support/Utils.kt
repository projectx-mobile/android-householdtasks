package com.projectx.householdtasks.presentation.support

import androidx.constraintlayout.widget.ConstraintLayout
import com.projectx.householdtasks.R

fun ConstraintLayout.setProgress(progress: Int) {
    when (progress) {
        1 -> {
            this.setBackgroundResource(R.drawable.task_uncompleted)
        }
        2 -> {
            this.setBackgroundResource(R.drawable.task_new)
        }
        3 -> {
            this.setBackgroundResource(R.drawable.task_completed)
        }
    }
}