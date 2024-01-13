package dev.rivu.composeclass1.userslist.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import dev.rivu.composeclass1.userslist.UsersListViewModel
import dev.rivu.composeclass1.userslist.data.model.UserDataModel

@Composable
fun PaginatedUsersListScreen(
    viewModel: UsersListViewModel = viewModel()
) {
    val state = viewModel.usersState

    LaunchedEffect(Unit) {
        viewModel.fetchUsers()
    }

    Box(modifier = Modifier
        .background(Color.White)
        .padding(15.dp)
    ) {
        when {
            state.isLoading -> {
                Text("Loading")
            }
            state.errorDetails != null -> {
                Text("Error ${state.errorDetails}")
            }
            !state.users.isNullOrEmpty() -> {
                val listState: LazyGridState = rememberLazyGridState()
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    state = listState,
                    contentPadding = PaddingValues(5.dp)
                ) {
                    items(state.users) {
                        UsersItem(it)
                    }
                    if (state.isPaginationLoading) {
                        item(
                            span = {
                                GridItemSpan(2)
                            }
                        ) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                            ) {
                                Text("Loading Pagination")
                            }
                        }
                    }
                }

                LaunchedEffect(listState.layoutInfo.visibleItemsInfo.lastIndex) {
                    if ((listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                            ?: 0) >= (state.users.size - 1)
                    ) {
                        viewModel.fetchUsers()
                    }
                    Log.d("Pagination", "${listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index} ${state.users.size}")
                }
            }
        }
    }
}

@Composable
fun UsersItem(user: UserDataModel) {
    Card(
        modifier = Modifier
            .size(250.dp)
            .padding(5.dp)
    ) {
        Column {
            AsyncImage(
                model = user.profilePicture,
                contentDescription = "User ${user.firstName} Profile Pic",
                modifier = Modifier.size(120.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(user.firstName + " " + user.lastName)
            Text(user.email)
            Text(user.job)
        }
    }
}