package com.projectx.householdtasks.presentation.fragment

import androidx.navigation.fragment.findNavController
import com.projectx.householdtasks.databinding.FragmentChooseLoginTypeBinding
import com.projectx.householdtasks.presentation.event.ChooseLoginTypeScreenEvent
import com.projectx.householdtasks.presentation.viewmodel.ChooseLoginTypeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChooseLoginTypeFragment :
    BaseFragment<FragmentChooseLoginTypeBinding, Nothing, ChooseLoginTypeScreenEvent>() {

    override fun getViewBinding() = FragmentChooseLoginTypeBinding.inflate(layoutInflater)

    override fun getBaseViewModel() = viewModel<ChooseLoginTypeViewModel>().value

    override fun bindUI() = super.bindUI().apply {
        layoutChooseLoginTypeGoogle.setOnClickListener {
            viewModel.onEvent(ChooseLoginTypeScreenEvent.LoginWithGoogle(findNavController()))
        }
        layoutChooseLoginTypeEmail.setOnClickListener {
            viewModel.onEvent(ChooseLoginTypeScreenEvent.LoginWithEmail(findNavController()))
        }
        textviewChooseLoginTypeCreateAccount.setOnClickListener {
            viewModel.onEvent(ChooseLoginTypeScreenEvent.CreateAccount(findNavController()))
        }
    }
}