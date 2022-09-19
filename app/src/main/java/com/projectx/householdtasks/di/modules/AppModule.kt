package com.projectx.householdtasks.di.modules

import com.projectx.householdtasks.data.example.repository.ExampleRepository
import com.projectx.householdtasks.presentation.viewmodel.MainViewModel
import com.projectx.householdtasks.presentation.viewmodel.ParentHomescreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { ExampleRepository(get()) }

    viewModel { MainViewModel() }
    viewModel { ParentHomescreenViewModel() }
}