package com.projectx.auth.presentation.fragment

import com.projectx.auth.databinding.FragmentCreateAccountFinishBinding
import com.projectx.auth.presentation.viewmodel.CreateAccountFinishViewModel
import com.projectx.common.presentation.fragment.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateAccountFinishFragment:
    BaseFragment<FragmentCreateAccountFinishBinding, CreateAccountFinishViewModel>(FragmentCreateAccountFinishBinding::inflate){

    override val viewModel by viewModel<CreateAccountFinishViewModel>()
}