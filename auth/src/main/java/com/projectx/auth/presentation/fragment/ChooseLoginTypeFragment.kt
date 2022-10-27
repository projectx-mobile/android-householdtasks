package com.projectx.auth.presentation.fragment

import androidx.appcompat.app.AppCompatActivity
import com.projectx.common.presentation.fragment.BaseFragment
import com.projectx.auth.databinding.FragmentChooseLoginTypeBinding
import com.projectx.common.presentation.navigation.NavEvent
import com.projectx.auth.presentation.viewmodel.ChooseLoginTypeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChooseLoginTypeFragment:
    BaseFragment<FragmentChooseLoginTypeBinding, ChooseLoginTypeViewModel>(
        FragmentChooseLoginTypeBinding::inflate
    ) {

    override val viewModel by viewModel<ChooseLoginTypeViewModel>()

    override fun FragmentChooseLoginTypeBinding.bindUI() {
        appbarChooseLoginType.toolbar.setNavigationOnClickListener {
            viewModel.navigate(NavEvent.Up)
        }
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