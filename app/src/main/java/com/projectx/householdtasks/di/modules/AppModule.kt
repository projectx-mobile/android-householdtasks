package com.projectx.householdtasks.di.modules

import com.projectx.auth.data.authentication.repository.ExampleRepository
import com.projectx.common.domain.use_case.GetFamilyMemberTestListUseCase
import com.projectx.common.domain.use_case.GetFamilyMembersUseCase
import com.projectx.common.domain.use_case.GetFaqListUseCase
import com.projectx.common.domain.use_case.GetUpdatesTestListUseCase
import com.projectx.common.domain.use_case.ValidateEmailUseCase
import com.projectx.common.domain.use_case.ValidatePasswordUseCase
import com.projectx.common.presentation.viewmodel.AccountStatusViewModel
import com.projectx.common.presentation.viewmodel.EditProfileEmailViewModel
import com.projectx.common.presentation.viewmodel.EditProfileViewModel
import com.projectx.common.presentation.viewmodel.ProfileViewModel
import com.projectx.common.presentation.viewmodel.SupportViewModel
import com.projectx.householdtasks.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { ExampleRepository(get()) }

    factory { GetFamilyMembersUseCase() }
    factory { GetFamilyMemberTestListUseCase() }
    factory { GetUpdatesTestListUseCase() }
    factory { ValidateEmailUseCase() }
    factory { ValidatePasswordUseCase() }
    factory { GetFaqListUseCase() }

    viewModel { MainViewModel() }
    viewModel { ProfileViewModel(get()) }
    viewModel { EditProfileViewModel() }
    viewModel { AccountStatusViewModel(get()) }
    viewModel { EditProfileEmailViewModel(get(), get()) }
    viewModel { SupportViewModel(get()) }
}