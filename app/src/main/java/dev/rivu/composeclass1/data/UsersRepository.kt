package dev.rivu.composeclass1.data

import dev.rivu.composeclass1.data.model.UserDataModel
import dev.rivu.composeclass1.data.remote.UsersRemoteDS

class UsersRepository(
    private val usersRemoteDS: UsersRemoteDS
) {
    suspend fun getUsersList(
        pageNo: Int = 0
    ): List<UserDataModel> {
        val limit = 10
        val offset = pageNo * limit

        val usersResponse = usersRemoteDS.getUsers(offset)

        return usersResponse.users.map {
            UserDataModel(
                id = it.id,
                firstName = it.firstName,
                lastName = it.lastName,
                email= it.email,
                profilePicture = it.profilePicture,
                job = it.job
            )
        }
    }
}