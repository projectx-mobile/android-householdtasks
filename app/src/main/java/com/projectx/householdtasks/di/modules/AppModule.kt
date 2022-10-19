package com.projectx.householdtasks.di.modules

import com.projectx.householdtasks.data.example.repository.ExampleRepository
import com.projectx.householdtasks.domain.use_case.ValidateEmailUseCase
import com.projectx.householdtasks.domain.use_case.ValidatePasswordUseCase
import com.projectx.householdtasks.presentation.viewmodel.*
import com.projectx.householdtasks.presentation.viewmodel.ChooseLoginTypeViewModel
import com.projectx.householdtasks.presentation.viewmodel.LoginViewModel
import com.projectx.householdtasks.presentation.viewmodel.ParentHomescreenViewModel
import com.projectx.householdtasks.presentation.viewmodel.OnBoardingImageViewModel
import com.projectx.householdtasks.presentation.viewmodel.OnBoardingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { ExampleRepository(get()) }

    single { ValidateEmailUseCase }
    single { ValidatePasswordUseCase }

    viewModel { OnBoardingViewModel() }
    viewModel { OnBoardingImageViewModel() }
    viewModel { ChooseLoginTypeViewModel() }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { ParentHomescreenViewModel() }
    viewModel { EditProfileViewModel() }
    viewModel { EditProfileEmailViewModel() }
    viewModel { EditProfilePasswordViewModel() }
    viewModel { AccountStatusViewModel() }
    viewModel { InviteUserByEmailViewModel() }
    viewModel { NotificationSharedViewModel() }
    viewModel { NotificationViewModel() }
    viewModel { ProfileViewModel() }
    viewModel { SupportViewModel() }

}