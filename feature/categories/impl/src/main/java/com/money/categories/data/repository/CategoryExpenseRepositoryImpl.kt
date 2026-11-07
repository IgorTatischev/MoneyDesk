package com.money.categories.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.money.categories.domain.model.CategoryModel
import com.money.common.Resource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class CategoryExpenseRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) : CategoryExpenseRepository {

    private fun requireUserId() =
        firebaseAuth.currentUser?.uid ?: throw IllegalStateException("User is not authorized")

    override fun getDefaultExpenseCategories(): Flow<Resource<List<CategoryModel>>> = callbackFlow {
        trySend(Resource.Loading())

        val defaultListener = firestore
            .collection(DEFAULT_EXPENSE_COLLECTION)
            .addSnapshotListener { defaultCategories, error ->

                if (error != null) {
                    trySend(Resource.Error(message = error.message ?: "Default categories load error"))
                    close()
                    return@addSnapshotListener
                }

                if (defaultCategories != null) {
                    val categories = defaultCategories.toObjects(CategoryModel::class.java)
                    trySend(Resource.Success(categories))
                }
            }

        awaitClose {
            defaultListener.remove()
        }
    }

    override fun getUserExpenseCategories(): Flow<Resource<List<CategoryModel>>> = callbackFlow {
        trySend(Resource.Loading())

        val userId = try {
            requireUserId()
        } catch (e: Exception) {
            trySend(Resource.Error(message = e.message ?: "User is not authorized"))
            close()
            return@callbackFlow
        }

        val userListener = firestore
            .collection(USER_COLLECTION)
            .document(userId)
            .collection(USER_EXPENSE_COLLECTION)
            .addSnapshotListener { userCategories, error ->

                if (error != null) {
                    trySend(Resource.Error(message = error.message ?: "User categories load error"))
                    close()
                    return@addSnapshotListener
                }

                if (userCategories != null) {
                    val categories = userCategories.toObjects(CategoryModel::class.java)
                    trySend(Resource.Success(categories))
                }
            }

        awaitClose {
            userListener.remove()
        }
    }

    override suspend fun addUserExpenseCategory(category: CategoryModel): Resource<Unit> {
        return try {
            val userId = requireUserId()
            val newCategory = hashMapOf(
                "name" to category.name,
                "isDefault" to category.isDefault,
                "color" to category.color,
                "iconChar" to category.iconChar
            )

            firestore.collection(USER_COLLECTION)
                .document(userId)
                .collection(USER_EXPENSE_COLLECTION)
                .add(newCategory)
                .await()

            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(message = e.message ?: "Unknown error")
        }
    }

    override suspend fun deleteUserExpenseCategory(categoryId: String): Resource<Unit> {
        return try {
            val userId = requireUserId()
            firestore.collection(USER_COLLECTION)
                .document(userId)
                .collection(USER_EXPENSE_COLLECTION)
                .document(categoryId)
                .delete()
                .await()

            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(message = e.message ?: "Unknown error")
        }
    }

    override suspend fun updateUserExpenseCategory(categoryId: String, category: CategoryModel): Resource<Unit> {
        return try {
            val userId = requireUserId()
            val updatedData = mapOf(
                "name" to category.name,
                "isDefault" to category.isDefault,
                "color" to category.color,
                "iconChar" to category.iconChar
            )

            firestore.collection(USER_COLLECTION)
                .document(userId)
                .collection(USER_EXPENSE_COLLECTION)
                .document(categoryId)
                .update(updatedData)
                .await()

            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(message = e.message ?: "Unknown error")
        }
    }

    companion object {
        const val USER_COLLECTION = "users"
        const val USER_EXPENSE_COLLECTION = "categories_expense"
        const val DEFAULT_EXPENSE_COLLECTION = "def_categories_expense"
    }
}
