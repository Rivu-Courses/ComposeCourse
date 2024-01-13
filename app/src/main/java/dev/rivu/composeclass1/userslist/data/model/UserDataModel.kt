package dev.rivu.composeclass1.userslist.data.model

data class UserDataModel (
    val id: Int = 0,
    val firstName: String,
    val lastName: String,
    val email: String,
    val profilePicture: String = "",
    val job: String = "",
)