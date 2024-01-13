package dev.rivu.composeclass1.userslist.data.model

import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @SerializedName("offset")
    val offset: Int = 0,
    @SerializedName("success")
    val success: Boolean = false,
    @SerializedName("limit")
    val limit: Int = 0,
    @SerializedName("message")
    val message: String = "",
    @SerializedName("total_users")
    val totalUsers: Int = 0,
    @SerializedName("users")
    val users: List<User>
)