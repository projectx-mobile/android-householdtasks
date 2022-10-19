package com.projectx.householdtasks.domain.use_case

import com.projectx.householdtasks.presentation.LoginPasswordResult

object ValidatePasswordUseCase : BaseUseCase<LoginPasswordResult, String>() {
    override suspend fun run(params: String): LoginPasswordResult {
        return when {
            params.isEmpty() -> LoginPasswordResult.Empty
            else -> {
                if (params.length >= MIN_PASSWORD_LENGTH) {
                    LoginPasswordResult.OK
                } else {
                    LoginPasswordResult.LengthError
                }
            }
        }
    }

    private const val MIN_PASSWORD_LENGTH = 8
}