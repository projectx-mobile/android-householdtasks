package com.projectx.householdtasks.presentation.viewmodel

import com.projectx.householdtasks.R
import com.projectx.householdtasks.presentation.event.InviteUserScreenEvent

class InviteUserViewModel : BaseViewModel<Nothing, InviteUserScreenEvent>() {
    override fun onEvent(event: InviteUserScreenEvent) {
        when (event) {
            is InviteUserScreenEvent.NavigateToInviteUserByEmail -> event.navController.navigate(R.id.action_inviteUserFragment_to_inviteUserByEmailFragment)
            else -> super.onEvent(event)
        }
    }
}