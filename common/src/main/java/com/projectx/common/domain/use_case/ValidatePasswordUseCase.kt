package com.projectx.common.domain.use_case

import com.projectx.common.presentation.viewmodel.EditProfileViewModel.Companion.LoginPasswordResult

class ValidatePasswordUseCase : BaseUseCase<LoginPasswordResult, String>() {
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

    private val MIN_PASSWORD_LENGTH = 8
}