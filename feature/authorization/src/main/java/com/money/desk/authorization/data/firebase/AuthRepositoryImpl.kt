package com.money.desk.authorization.data.firebase

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthCredential
import com.money.desk.authorization.domain.model.UserModel
import com.money.desk.authorization.domain.repository.AuthRepository
import com.money.common.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : AuthRepository {

    override fun signUp(userModel: UserModel): Flow<Resource<AuthResult>> {
        TODO("Not yet implemented")
    }

    override fun singIn(email: String, password: String): Flow<Resource<AuthResult>> {
        TODO("Not yet implemented")
    }

    override fun googleSignIn(credential: GoogleAuthCredential): Flow<Resource<AuthResult>> {
        TODO("Not yet implemented")
    }

    override fun signOut() {
        TODO("Not yet implemented")
    }

}