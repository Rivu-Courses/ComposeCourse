package dev.rivu.composeclass1.userslist

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.rivu.composeclass1.userslist.data.UsersRepository
import dev.rivu.composeclass1.userslist.data.model.UserDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UsersListViewModel @Inject constructor(
    private val usersRepo: UsersRepository
) : ViewModel() {
    var usersState: UsersState by mutableStateOf(UsersState())
        private set

    private var pageNo: Int = 0

    fun fetchUsers(p2r: Boolean = false) {
        usersState = if (usersState.users.isNullOrEmpty()) {
            UsersState(
                isLoading = true
            )
        } else if (p2r) {
            usersState.copy(
                isP2RLoading = true
            )
        } else {
            usersState.copy(
                isPaginationLoading = true
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                delay(1000)
                val usersListModel = usersRepo.getUsersList(
                    if (p2r) {
                        0
                    } else {
                        pageNo
                    }
                )
                val users = usersListModel.users
                Log.d("Pagination", "Page $pageNo")

                val updatedState = usersState.copy(
                    users = if (p2r) users else (usersState.users ?: emptyList()) + users,
                    isPaginationLoading = false,
                    isLoading = false,
                    errorDetails = null,
                    isP2RLoading = false,
                    canPaginate = usersListModel.currentPage < usersListModel.totalPageCount
                )

                withContext(Dispatchers.Main) {
                    usersState = updatedState
                }
                if (p2r) {
                    pageNo = 1
                } else {
                    pageNo++
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    usersState = usersState.copy(
                        errorDetails = Error.CustomError(e.message ?: "Something went wrong"),
                        isLoading = false,
                        isPaginationLoading = false,
                        isP2RLoading = false,
                    )
                }
                pageNo = 0
            }
        }
    }
}

data class UsersState(
    val users: List<UserDataModel>? = null,
    val isLoading: Boolean = false,
    val isPaginationLoading: Boolean = false,
    val isP2RLoading: Boolean = false,
    val errorDetails: Error? = null,
    val canPaginate: Boolean = true,
)

sealed class Error(
    open val message: String
) {
    object ServerError : Error(message = "Server Error Happened.\nPlease Try Again")

    data class CustomError(override val message: String) : Error(message)
}