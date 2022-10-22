package com.projectx.householdtasks.presentation.fragment

import androidx.appcompat.app.AppCompatActivity
import com.projectx.householdtasks.databinding.FragmentChooseLoginTypeBinding
import com.projectx.householdtasks.presentation.viewmodel.ChooseLoginTypeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChooseLoginTypeFragment :
    BaseFragment<FragmentChooseLoginTypeBinding, ChooseLoginTypeViewModel>(
        FragmentChooseLoginTypeBinding::inflate
    ) {

    override val viewModel by viewModel<ChooseLoginTypeViewModel>()

    override fun FragmentChooseLoginTypeBinding.bindUI() {
        layoutChooseLoginTypeGoogle.setOnClickListener {
            viewModel.loginWithGoogle(requireActivity() as AppCompatActivity)
        }
        layoutChooseLoginTypeEmail.setOnClickListener {
            viewModel.loginWithEmail()
        }
        textviewChooseLoginTypeCreateAccount.setOnClickListener {
            viewModel.createAccount()
        }
    }
}