package com.projectx.householdtasks.di.modules

import com.projectx.auth.data.authentication.repository.ExampleRepository
import com.projectx.common.domain.use_case.GetFamilyMemberTestListUseCase
import com.projectx.common.domain.use_case.GetFamilyMembersUseCase
import com.projectx.common.domain.use_case.GetUpdatesTestListUseCase
import com.projectx.householdtasks.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { ExampleRepository(get()) }

    factory { GetFamilyMembersUseCase() }
    factory { GetFamilyMemberTestListUseCase() }
    factory { GetUpdatesTestListUseCase() }

    viewModel { MainViewModel() }
}