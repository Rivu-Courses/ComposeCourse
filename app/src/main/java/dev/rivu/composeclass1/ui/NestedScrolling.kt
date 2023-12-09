package dev.rivu.composeclass1.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NestedScrolling() {
    val nestedScrollingState = NestedScrollingState(
        items = (0..15).toList().map { item ->
            NestedScrollingItem(
                itemName = "Item $item",
                subItems = (0..5).toList().map {
                    "$item Sub Item $it"
                }
            )
        }
    )

    LazyVerticalGrid(GridCells.Fixed(2)) {

        item(span = {
            GridItemSpan(2)
        }) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Full With", modifier = Modifier.align(Alignment.Center))
            }
        }

        itemsIndexed(nestedScrollingState.items) { itemIndex, item ->
            Column {
                Text(item.itemName)
                item.subItems.forEachIndexed() { subItemIndex, text ->
                    key(subItemIndex) {
                        Column {
                            Text(text)
                            AsyncImage(
                                model = "https://picsum.photos/200/300?random=$itemIndex$subItemIndex",
                                contentDescription = text,
                                modifier = Modifier.size(200.dp)
                            )
                        }
                    }
                }
            }
        }

    }
}

data class NestedScrollingState(
    val items: List<NestedScrollingItem>
)

data class NestedScrollingItem(
    val itemName: String,
    val subItems: List<String>
)