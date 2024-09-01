package com.money.desk.authorization.domain.use_cases

import com.money.desk.authorization.domain.repository.AuthRepository
import javax.inject.Inject

internal class ResetPasswordUseCase @Inject constructor(private val authRepository: AuthRepository) {

    suspend operator fun invoke(email: String) = runCatching {
        authRepository.resetPassword(email)
    }
}