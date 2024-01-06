package dev.rivu.composeclass1.data.remote

import dev.rivu.composeclass1.data.model.UsersResponse

open class UsersRemoteDS(
    private val usersService: UsersService
) {
    suspend fun getUsers(offset: Int = 0): UsersResponse {
        return usersService.getUsers()
    }
}