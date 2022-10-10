package com.projectx.householdtasks.di.modules

import com.projectx.householdtasks.data.example.repository.ExampleRepository
import com.projectx.householdtasks.presentation.viewmodel.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { ExampleRepository(get()) }

    viewModel { MainViewModel() }
    viewModel { OnBoardingViewModel() }
    viewModel { OnBoardingImageViewModel() }
    viewModel { ChooseLoginTypeViewModel() }
    viewModel { LoginViewModel() }
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