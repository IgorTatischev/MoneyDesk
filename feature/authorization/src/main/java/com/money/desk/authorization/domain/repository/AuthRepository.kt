package com.money.desk.authorization.domain.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.GoogleAuthCredential
import com.money.desk.authorization.domain.model.UserModel
import com.money.common.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun signUp(userModel: UserModel): Flow<Resource<AuthResult>>

    fun singIn(email: String, password: String): Flow<Resource<AuthResult>>

    fun googleSignIn(credential: GoogleAuthCredential): Flow<Resource<AuthResult>>

    fun signOut()
}