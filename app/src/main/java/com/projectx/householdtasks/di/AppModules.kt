package com.projectx.householdtasks.di

import com.projectx.householdtasks.di.modules.appModule
import com.projectx.householdtasks.di.modules.authModule
import com.projectx.householdtasks.di.modules.childModule
import com.projectx.householdtasks.di.modules.parentModule

internal val appModules = listOf(
    appModule,
    authModule,
    childModule,
    parentModule
)