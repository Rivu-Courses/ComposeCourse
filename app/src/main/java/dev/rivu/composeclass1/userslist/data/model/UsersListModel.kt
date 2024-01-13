package dev.rivu.composeclass1.userslist.data.model

data class UsersListModel(
    val currentPage: Int,
    val totalPageCount: Int,
    val users: List<UserDataModel>
)
