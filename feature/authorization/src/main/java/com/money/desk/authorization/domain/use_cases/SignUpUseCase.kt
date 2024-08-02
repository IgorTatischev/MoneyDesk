package com.money.desk.authorization.domain.use_cases

import com.google.firebase.auth.AuthResult
import com.money.common.Resource
import com.money.desk.authorization.domain.model.UserModel
import com.money.desk.authorization.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal class SignUpUseCase @Inject constructor(private val authRepository: AuthRepository) {

    operator fun invoke(user: UserModel): Flow<Resource<AuthResult>> = flow {
        emit(Resource.Loading())
        val result = authRepository.signUp(user)
        emit(Resource.Success(result))
    }.catch {
        emit(Resource.Error(message = it.message.toString()))
    }.flowOn(Dispatchers.IO)
}