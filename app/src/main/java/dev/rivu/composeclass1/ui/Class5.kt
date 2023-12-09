package dev.rivu.composeclass1.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.rivu.composeclass1.ui.theme.ComposeClass1Theme

@Stable
data class HomePageState(
    val topsectionState: MutableState<String>,
    val leftSectionState: List<String>,
    val rightSectionState: List<String>,
)

@Composable
fun HomePage(homePageState: HomePageState) {
    Column {
        TopSection(homePageState, Modifier.fillMaxWidth())
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            LeftListSection(homePageState)
            RightListSection(homePageState)
        }
    }
}

@Composable
fun TopSection(
    state: HomePageState,
    modifier: Modifier
) {
    Box(modifier = modifier.border(1.dp, Color.Red)) {
        Text(state.topsectionState.value, modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun LeftListSection(state: HomePageState,) {
    LazyColumn {
        items(state.leftSectionState) {
            Text(it)
        }
    }
}

@Composable
fun RightListSection(state: HomePageState,) {
    LazyColumn {
        items(state.rightSectionState) {
            Text(it)
        }
    }
}

@Composable
fun BottomSection() {

}

@Preview
@Composable
fun HomePagePreview() {
    ComposeClass1Theme {
        Box(modifier = Modifier.background(Color.White)) {
            HomePage(
                HomePageState(
                    topsectionState = remember { mutableStateOf("Top Section") },
                    leftSectionState = (1..10).toList().map {
                        "Left Section Item $it"
                    },
                    rightSectionState = (1..10).toList().map {
                        "Right Section Item $it"
                    },
                )
            )
        }
    }
}