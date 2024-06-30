package com.money.desk.authorization.domain.model

data class UserModel(
    val userId: String? = null,
    val login: String,
    val password: String,
    val name: String,
    //todo add image
)
