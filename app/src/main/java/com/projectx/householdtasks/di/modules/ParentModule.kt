package com.projectx.householdtasks.di.modules

import com.projectx.householdtasks.parent.presentation.viewmodel.ParentHomescreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val parentModule = module {
    viewModel { ParentHomescreenViewModel(get(), get()) }
}