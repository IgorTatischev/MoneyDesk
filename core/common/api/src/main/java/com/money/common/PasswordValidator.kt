package com.money.common

interface PasswordValidator {

    fun execute(password: String): PasswordValidationState
}

data class PasswordValidationState(
    val hasMinimum: Boolean = false,
    val hasCapitalizedLetter: Boolean = false,
    val successful: Boolean = false
)
