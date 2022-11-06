package com.projectx.auth.presentation.fragment

import android.graphics.drawable.InsetDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.projectx.auth.R
import com.projectx.auth.databinding.DialogNotificationPermissionBinding
import com.projectx.auth.databinding.FragmentCreateAccountNotificationsBinding
import com.projectx.auth.presentation.viewmodel.CreateAccountNotificationsViewModel
import com.projectx.common.presentation.fragment.BaseFragment
import com.projectx.common.presentation.navigation.NavEvent
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateAccountNotificationsFragment:
    BaseFragment<FragmentCreateAccountNotificationsBinding, CreateAccountNotificationsViewModel>(FragmentCreateAccountNotificationsBinding::inflate){
    override val viewModel by viewModel<CreateAccountNotificationsViewModel>()

    override fun FragmentCreateAccountNotificationsBinding.bindUI() {
        appbarCreateAccountNotifications.signUpToolbar.setNavigationOnClickListener {
            viewModel.navigate(NavEvent.Up)
        }
        buttonPermitNotifications.setOnClickListener {
            createDialogNotificationPermission()
        }
    }

    override fun CreateAccountNotificationsViewModel.subscribeUI() {

    }

    private fun createDialogNotificationPermission() {
        val dialogBinding = DialogNotificationPermissionBinding.inflate(LayoutInflater.from(requireContext()))

        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .setCancelable(false)
            .show()

        val inset = InsetDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.dialog_corner
            ), 40
        )
        alertDialog.window?.setBackgroundDrawable(inset)
        with(dialogBinding) {
            negativeButton.setOnClickListener {
                viewModel.goToFinishFragment()
                alertDialog.dismiss()
            }
            buttonPermit.setOnClickListener {
                viewModel.goToFinishFragment()
                alertDialog.dismiss()
            }
        }
    }
}