package com.projectx.householdtasks.di.modules

import com.projectx.auth.presentation.viewmodel.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    viewModel { OnBoardingViewModel() }
    viewModel { OnBoardingImageViewModel() }
    viewModel { ChooseLoginTypeViewModel() }
    viewModel { LoginViewModel() }
    viewModel { SignUpWithEmailViewModel() }
    viewModel { SignUpConfirmationViewModel() }
    viewModel { CreateAccountViewModel() }
}