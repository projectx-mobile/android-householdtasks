package com.projectx.householdtasks.presentation.viewmodel

import android.widget.Button
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.projectx.householdtasks.R
import com.projectx.householdtasks.presentation.Role

class AccountStatusViewModel : BaseViewModel() {

    private var _role = MutableLiveData(Role.PARENT)
    val role: LiveData<Role> = _role

    fun setRole(role: Role) {
        _role.value = role
    }

//    fun setPersonRole(buttonParent: Button, buttonChild: Button) {
//        buttonParent.setTextColor(
//            ContextCompat.getColor(requireContext(), (R.color.dark_text_color))
//        )
//        buttonChild.setTextColor(
//            ContextCompat.getColor(requireContext(), (R.color.white))
//        )
//        buttonChild.isSelected = true
//        buttonParent.isSelected = false
//    }

}