package com.money.desk.authorization.domain.repository

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.money.desk.authorization.domain.model.UserModel

internal interface AuthRepository {

    suspend fun signUp(user: UserModel): AuthResult

    suspend fun singIn(email: String, password: String): AuthResult

    suspend fun googleSignIn(credential: AuthCredential): AuthResult

    //todo reset password fun
}