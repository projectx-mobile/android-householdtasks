package com.projectx.householdtasks.presentation.adapter

import com.projectx.householdtasks.R

enum class SettingModel(
    val icon: Int,
    val title: Int
) {
    FAQ(R.drawable.ic_support, R.string.faq),
    PRIVACY_POLICY(R.drawable.ic_privacy_policy, R.string.privacy_policy),
    FEEDBACK(R.drawable.ic_feedback, R.string.feedback)
}

val supportSettingsList = listOf(SettingModel.FAQ, SettingModel.PRIVACY_POLICY, SettingModel.FEEDBACK)

