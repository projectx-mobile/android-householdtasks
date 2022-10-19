package com.projectx.householdtasks.domain.use_case

import com.projectx.householdtasks.presentation.LoginEmailResult

object ValidateEmailUseCase : BaseUseCase<LoginEmailResult, String>() {
    override suspend fun run(params: String): LoginEmailResult {
        return when {
            params.isEmpty() -> LoginEmailResult.Empty
            else -> {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(params.trim()).matches()) {
                    LoginEmailResult.OK
                } else {
                    LoginEmailResult.InvalidEmailError
                }
            }
        }
    }
}
