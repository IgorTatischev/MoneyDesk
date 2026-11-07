package com.money.common

import javax.inject.Inject

internal class PasswordValidatorImpl @Inject constructor() : PasswordValidator {

    override fun execute(password: String): PasswordValidationState {

        val validateCapitalizedLetter = validateCapitalizedLetter(password)
        val validateMinimum = validateMinimum(password)

        val hasError = listOf(
            validateMinimum,
            validateCapitalizedLetter,
        ).all { it }

        return PasswordValidationState(
            hasMinimum = validateMinimum,
            hasCapitalizedLetter = validateCapitalizedLetter,
            successful = hasError
        )
    }

    private fun validateCapitalizedLetter(password: String): Boolean =
        password.matches(Regex(".*[A-Z].*"))

    private fun validateMinimum(password: String): Boolean =
        password.matches(Regex(".{6,}"))
}
