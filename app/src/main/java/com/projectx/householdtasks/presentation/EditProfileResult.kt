package com.projectx.householdtasks.presentation

sealed class NameValidationResult {
    object OK : NameValidationResult()
    object LengthError: NameValidationResult()
    object NoLettersError: NameValidationResult()
    object InvalidCharacterError: NameValidationResult()
}

sealed class LoginEmailResult {
    object OK : LoginEmailResult()
    object Empty : LoginEmailResult()
    object InvalidEmailError : LoginEmailResult()
}
sealed class LoginPasswordResult {
    object OK : LoginPasswordResult()
    object Empty : LoginPasswordResult()
    object LengthError: LoginPasswordResult()
}

sealed class EmailValidationResult {
    object OK : EmailValidationResult()
    object InvalidEmailError: EmailValidationResult()
}

sealed class CurrentPasswordValidationResult {
    object OK : CurrentPasswordValidationResult()
    object LengthError: CurrentPasswordValidationResult()
    object InvalidPasswordError: CurrentPasswordValidationResult()
}

sealed class NewPasswordValidationResult {
    object OK : NewPasswordValidationResult()
    object LengthError: NewPasswordValidationResult()
}

sealed class PasswordConfirmationValidationResult {
    object OK : PasswordConfirmationValidationResult()
    object LengthError: PasswordConfirmationValidationResult()
    object PasswordsMismatchError: PasswordConfirmationValidationResult()
}

sealed class RequestResult {
    object Success: RequestResult()
    object RequestFailedError: RequestResult()
}


data class EditProfileResult(
    val nameValidationResult: NameValidationResult,
    val emailValidationResult: EmailValidationResult,
    val currentPasswordValidationResult: CurrentPasswordValidationResult,
    val newPasswordValidationResult: NewPasswordValidationResult,
    val passwordConfirmationValidationResult: PasswordConfirmationValidationResult,
    val requestResult: RequestResult
)

