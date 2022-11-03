package com.projectx.common.domain.use_case


class ValidateEmailUseCase :
    BaseUseCase<ValidateEmailUseCase.EmailValidationResult, String>() {
    override suspend fun run(params: String): EmailValidationResult {
        return when {
            params.isEmpty() -> EmailValidationResult.EMPTY
            else -> {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(params.trim()).matches()) {
                    EmailValidationResult.OK
                } else {
                    EmailValidationResult.ERROR
                }
            }
        }
    }

    enum class EmailValidationResult {
        EMPTY,
        OK,
        ERROR
    }
}