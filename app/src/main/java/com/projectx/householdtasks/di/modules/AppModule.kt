package com.projectx.householdtasks.di.modules

import com.projectx.householdtasks.data.example.repository.ExampleRepository
import com.projectx.householdtasks.presentation.fragment.EditProfileEmailFragment
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

    viewModel { OnBoardingViewModel() }
    viewModel { OnBoardingImageViewModel() }
    viewModel { ChooseLoginTypeViewModel() }
    viewModel { LoginViewModel() }
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