package com.projectx.householdtasks.di

import com.projectx.householdtasks.di.modules.appModule
import com.projectx.householdtasks.di.modules.childModule
import com.projectx.householdtasks.di.modules.parentModule
import org.koin.core.module.Module

internal val appModules: List<Module> = appModule +
        childModule +
        parentModule