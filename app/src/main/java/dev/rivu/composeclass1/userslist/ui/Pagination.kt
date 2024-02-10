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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.theapache64.rebugger.Rebugger
import dev.rivu.composeclass1.userslist.UsersListViewModel
import dev.rivu.composeclass1.userslist.data.model.UserDataModel
import kotlinx.collections.immutable.ImmutableList

@Composable
fun PaginatedUsersListScreen(
    viewModel: UsersListViewModel = viewModel()
) {
    val state = viewModel.usersState

    LaunchedEffect(Unit) {
        viewModel.fetchUsers()
    }

    val fetchNextPage: ()->Unit by remember {
        mutableStateOf(
            {
                viewModel.fetchUsers()
            }
        )
    }

    val onRefresh: ()->Unit by remember {
        mutableStateOf(
            {
                viewModel.fetchUsers(true)
            }
        )
    }

    Box(modifier = Modifier
        .background(Color.White)
        .padding(15.dp)
    ) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.matchParentSize().padding(15.dp).align(Alignment.Center)
                )
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
                    fetchNextPage = fetchNextPage,
                    onRefresh = onRefresh
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShowUsersList(
    users: ImmutableList<UserDataModel>,
    isPaginationLoading: Boolean,
    canPaginate: Boolean,
    isRefreshing: Boolean,
    modifier: Modifier = Modifier,
    fetchNextPage: () -> Unit,
    onRefresh: () -> Unit
) {

    var shouldFetchNextPage by remember(users.size) {
        mutableStateOf(false)
    }

    LaunchedEffect(shouldFetchNextPage) {
        if (shouldFetchNextPage) {
            Log.d("Pagination", "Fetch Next Page ${users.size}")
            fetchNextPage()
        }
    }

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

        trackScroll(
            listState = listState,
            maxSize = users.size - 1,
            onPageEnd = {
                if (canPaginate) {
                    shouldFetchNextPage = true
                }
            }
        )

        Rebugger(
            mapOf (
                "users" to users,
                "isPaginationLoading" to isPaginationLoading,
                "canPaginate" to canPaginate,
                "isRefreshing" to isRefreshing,
                "modifier" to modifier,
                "fetchNextPage" to fetchNextPage,
                "onRefresh" to onRefresh,
                "refershState" to refershState,
                "listState" to listState,
                "shouldFetchNextPage" to shouldFetchNextPage
            )
        )
    }
}

@Composable
fun trackScroll(listState: LazyGridState, maxSize: Int, onPageEnd: ()->Unit) {
    LaunchedEffect(listState.layoutInfo.visibleItemsInfo.lastIndex) {
        if ((listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                ?: 0) >= (maxSize)
        ) {
            Log.d("Pagination", "lastIndex : ${listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index} | users size $maxSize")
            onPageEnd()
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