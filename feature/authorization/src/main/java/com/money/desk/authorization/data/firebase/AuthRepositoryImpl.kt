package com.money.desk.authorization.data.firebase

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.money.common.Constants.USERS
import com.money.desk.authorization.domain.model.UserModel
import com.money.desk.authorization.domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
) : AuthRepository {

    override suspend fun signUp(user: UserModel): AuthResult {
        return firebaseAuth.createUserWithEmailAndPassword(user.login, user.password)
            .addOnSuccessListener {
                val firebaseUser = firebaseAuth.currentUser
                if (firebaseUser != null) {
                    user.userId = firebaseUser.uid
                    firebaseFirestore
                        .collection(USERS)
                        .document(firebaseUser.uid)
                        .set(user)
                }
            }.await()
    }

    override suspend fun singIn(email: String, password: String): AuthResult =
        firebaseAuth.signInWithEmailAndPassword(email, password).await()

    override suspend fun googleSignIn(credential: AuthCredential): AuthResult =
        firebaseAuth.signInWithCredential(credential).await()

    override suspend fun resetPassword(email: String) {
        firebaseAuth.sendPasswordResetEmail(email).await()
        return
    }
}