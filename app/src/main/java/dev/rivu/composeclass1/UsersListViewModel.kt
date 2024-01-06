package dev.rivu.composeclass1

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.rivu.composeclass1.data.UsersRepository
import dev.rivu.composeclass1.data.model.UserDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsersListViewModel(
    private val usersRepo: UsersRepository
): ViewModel() {
    private val _usersState: MutableState<UsersState> = mutableStateOf(UsersState())
    val usersState: State<UsersState> = _usersState

    private var pageNo: Int = 0

    fun fetchUsers() {
        _usersState.value = if (usersState.value.users.isNullOrEmpty()) {
            UsersState(
                isLoading = true
            )
        } else {
            usersState.value.copy(
                isPaginationLoading = true
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val users = usersRepo.getUsersList(pageNo)

                val updatedState = usersState.value.copy(
                    users = (usersState.value.users ?: emptyList()) + users,
                    isPaginationLoading = false,
                    isLoading = false,
                    errorDetails = null
                )

                withContext(Dispatchers.Main) {
                    _usersState.value = updatedState
                }
                pageNo++
            } catch(e: Exception) {
                withContext(Dispatchers.Main) {
                    _usersState.value = usersState.value.copy(
                        errorDetails = e.message ?: "Something went wrong",
                        isLoading = false,
                        isPaginationLoading = false
                    )
                }
                pageNo = 0
            }
        }
    }
}

data class UsersState (
    val users: List<UserDataModel>? = null,
    val isLoading: Boolean = false,
    val isPaginationLoading: Boolean = false,
    val isP2RLoading: Boolean = false,
    val errorDetails: String? = null,
)