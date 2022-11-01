package com.projectx.householdtasks.di.modules

import com.projectx.auth.presentation.viewmodel.ChooseLoginTypeViewModel
import com.projectx.auth.presentation.viewmodel.LoginViewModel
import com.projectx.auth.presentation.viewmodel.OnBoardingImageViewModel
import com.projectx.auth.presentation.viewmodel.OnBoardingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    viewModel { OnBoardingViewModel() }
    viewModel { OnBoardingImageViewModel() }
    viewModel { ChooseLoginTypeViewModel() }
    viewModel { LoginViewModel() }
}