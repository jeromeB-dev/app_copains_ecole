package com.example.app_copains_ecole.model

import java.io.Serializable

data class UserBean(
    var id: Long? = null,
    var pseudo: String? = null,
    var password: String? = null,
    var session: String? = null,
    var longitude: Double? = null,
    var latitude: Double? = null,
    var group_users: Int? = null,
): Serializable


