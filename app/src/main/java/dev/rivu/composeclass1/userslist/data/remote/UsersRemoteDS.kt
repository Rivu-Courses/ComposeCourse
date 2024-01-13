package dev.rivu.composeclass1.userslist.data.remote

import dev.rivu.composeclass1.userslist.data.model.UsersResponse
import javax.inject.Inject

open class UsersRemoteDS @Inject constructor(
    private val usersService: UsersService
) {
    suspend fun getUsers(offset: Int = 0): UsersResponse {
        return usersService.getUsers(offset)
    }
}