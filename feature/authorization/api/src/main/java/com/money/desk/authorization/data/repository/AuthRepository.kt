package com.money.desk.authorization.data.repository

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.money.desk.authorization.domain.model.UserModel

interface AuthRepository {

    suspend fun signUp(user: UserModel): AuthResult

    suspend fun singIn(email: String, password: String): AuthResult

    suspend fun googleSignIn(credential: AuthCredential): AuthResult

    suspend fun resetPassword(email: String)
}