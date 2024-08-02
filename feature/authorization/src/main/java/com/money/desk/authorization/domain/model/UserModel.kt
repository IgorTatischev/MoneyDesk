package com.money.desk.authorization.domain.model

internal data class UserModel(
    var userId: String? = null,
    val login: String,
    val password: String,
    val name: String,
    //todo add image
)
