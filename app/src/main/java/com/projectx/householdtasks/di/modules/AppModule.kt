package com.projectx.householdtasks.di.modules

import com.projectx.auth.data.authentication.repository.ExampleRepository
import com.projectx.auth.presentation.viewmodel.ChooseLoginTypeViewModel
import com.projectx.auth.presentation.viewmodel.LoginViewModel
import com.projectx.auth.presentation.viewmodel.OnBoardingImageViewModel
import com.projectx.auth.presentation.viewmodel.OnBoardingViewModel
import com.projectx.householdtasks.presentation.viewmodel.MainViewModel
import com.projectx.parent.presentation.viewmodel.ParentHomescreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { ExampleRepository(get()) }

    viewModel { MainViewModel() }

    viewModel { OnBoardingViewModel() }
    viewModel { OnBoardingImageViewModel() }
    viewModel { ChooseLoginTypeViewModel() }
    viewModel { LoginViewModel() }

    viewModel { ParentHomescreenViewModel() }
}