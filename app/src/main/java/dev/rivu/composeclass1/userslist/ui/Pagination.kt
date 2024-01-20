package dev.rivu.composeclass1.userslist.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
                Text("Error ${state.errorDetails.message}")
            }
            !state.users.isNullOrEmpty() -> {
                ShowUsersList(
                    state.users,
                    state.isPaginationLoading,
                    state.canPaginate,
                    state.isP2RLoading,
                    modifier = Modifier.fillMaxSize(),
                    fetchNextPage = {
                        viewModel.fetchUsers()
                    },
                    onRefresh = {
                        viewModel.fetchUsers(true)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShowUsersList(
    users: List<UserDataModel>,
    isPaginationLoading: Boolean,
    canPaginate: Boolean,
    isRefreshing: Boolean,
    modifier: Modifier = Modifier,
    fetchNextPage: () -> Unit,
    onRefresh: () -> Unit
) {
    val refershState = rememberPullRefreshState(isRefreshing, onRefresh = onRefresh)
    Box(
        modifier = modifier.pullRefresh(refershState, true)
    ) {
        val listState: LazyGridState = rememberLazyGridState()
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            state = listState,
            contentPadding = PaddingValues(5.dp)
        ) {
            items(users) {
                UsersItem(it)
            }
            if (isPaginationLoading) {
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

        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = refershState,
            modifier = Modifier.align(Alignment.TopCenter)
        )

        LaunchedEffect(listState.layoutInfo.visibleItemsInfo.lastIndex) {
            if (canPaginate && (listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                    ?: 0) >= (users.size - 1)
            ) {
                fetchNextPage()
            }
            Log.d("Pagination", "${listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index} ${users.size}")
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