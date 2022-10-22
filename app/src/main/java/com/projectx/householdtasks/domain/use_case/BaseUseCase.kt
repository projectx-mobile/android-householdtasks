package com.projectx.householdtasks.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

sealed class BaseUseCase<out Type, in Params> where Type : Any? {

    abstract suspend fun run(params: Params): Type

    fun execute(params: Params): Flow<Type> = flow {
        emit(run(params))
    }
}
