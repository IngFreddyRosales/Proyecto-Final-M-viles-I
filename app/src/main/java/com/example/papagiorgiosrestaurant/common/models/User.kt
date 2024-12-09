package com.example.papagiorgiosrestaurant.common.models

import com.google.gson.annotations.SerializedName

typealias users = ArrayList<User>

data class User(
    val id: Int? = null,
    var access_token: String = "",
    val name: String,
    val email: String,
    val password: String,
    val profile: Profile
)

