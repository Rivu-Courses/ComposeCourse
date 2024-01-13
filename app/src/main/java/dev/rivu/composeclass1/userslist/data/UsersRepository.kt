package dev.rivu.composeclass1.userslist.data

import android.util.Log
import dagger.hilt.android.scopes.ViewModelScoped
import dev.rivu.composeclass1.userslist.data.model.UserDataModel
import dev.rivu.composeclass1.userslist.data.model.UsersListModel
import dev.rivu.composeclass1.userslist.data.remote.UsersRemoteDS
import javax.inject.Inject

@ViewModelScoped
class UsersRepository @Inject constructor(
    private val usersRemoteDS: UsersRemoteDS
) {
    suspend fun getUsersList(
        pageNo: Int = 0
    ): UsersListModel {
        val limit = 10
        val offset = pageNo * limit

        val usersResponse = usersRemoteDS.getUsers(offset)

        Log.d("Pagination", "offset $offset")

        return UsersListModel(
            currentPage = pageNo,
            totalPageCount = usersResponse.totalUsers / limit,
            users = usersResponse.users.map {
                UserDataModel(
                    id = it.id,
                    firstName = it.firstName,
                    lastName = it.lastName,
                    email= it.email,
                    profilePicture = it.profilePicture,
                    job = it.job
                )
            }
        )
    }
}