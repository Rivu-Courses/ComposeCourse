package dev.rivu.composeclass1.userslist.data.remote

import dev.rivu.composeclass1.userslist.data.model.UsersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersService {
    @GET("sample-data/users")
    suspend fun getUsers(@Query("offset") offset: Int = 0): UsersResponse
}