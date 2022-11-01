package com.projectx.common.presentation.model

import com.projectx.common.R

enum class SettingModel(
    val icon: Int,
    val title: Int
) {
    FAQ(R.drawable.ic_support, R.string.faq),
    PRIVACY_POLICY(R.drawable.ic_privacy_policy, R.string.privacy_policy),
    FEEDBACK(R.drawable.ic_feedback, R.string.feedback),

    EMAIL(R.drawable.ic_email_black, R.string.email),
    PASSWORD(R.drawable.ic_password, R.string.password),
    PARENT(R.drawable.ic_person, R.string.parent),
    LINKED_ACCOUNTS(R.drawable.ic_link, R.string.linked_accounts),
    NOTIFICATIONS(R.drawable.ic_notification, R.string.notifications),

    LANGUAGE(R.drawable.ic_language, R.string.choose_language),
    SUPPORT(R.drawable.ic_support, R.string.support)
}

val supportSettingsList =
    listOf(SettingModel.FAQ, SettingModel.PRIVACY_POLICY, SettingModel.FEEDBACK)

val profileSettingList = listOf(
    SettingModel.EMAIL,
    SettingModel.PASSWORD,
    SettingModel.PARENT,
    SettingModel.LINKED_ACCOUNTS,
    SettingModel.NOTIFICATIONS
)

val profileOtherSettingList = listOf(
    SettingModel.LANGUAGE,
    SettingModel.SUPPORT
)

