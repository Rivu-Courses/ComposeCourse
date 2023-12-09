package dev.rivu.composeclass1.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.rivu.composeclass1.R
import dev.rivu.composeclass1.ui.theme.ComposeClass1Theme

@Composable
fun UserInteraction(
    modifier: Modifier = Modifier,
    navController: NavHostController? = null
) {
    var count by rememberSaveable {
        mutableIntStateOf(0)
    }

    var text by rememberSaveable {
        mutableStateOf("")
    }

    var items by rememberSaveable {
        mutableStateOf(listOf<String>())
    }

    val context = LocalContext.current
    Box(modifier = modifier.background(Color.White)) {
        Text("Count $count", modifier = Modifier.align(Alignment.TopStart))


        TextFieldDemo(
            textState = text,
            modifier = Modifier.align(Alignment.TopEnd).width(250.dp),
            onTextChange = {
                text = it
            }
        )
        Button(
            onClick = {
                count++
                items += text
                text = ""
            },
            modifier = Modifier.align(Alignment.Center)
        ) {
            Icon(
                painter = painterResource(androidx.core.R.drawable.ic_call_answer),
                contentDescription = "Some Icon",
            )
            Text("Click Me")
        }
        navController?.let {
            Button(
                onClick = {
                    val parameter = "From Last Class"
                    navController.navigate("screen2?parameter=$parameter")
                },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    painter = painterResource(androidx.core.R.drawable.ic_call_answer),
                    contentDescription = "Some Icon",
                )
                Text("Navigate to Screen 2")
            }
        }


        ListDemo(
            listOfStrings = items,
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        Image(
            painter = painterResource(R.drawable.rivu),
            contentDescription = "Some Icon",
            modifier = Modifier.align(Alignment.BottomStart).size(100.dp).clip(CircleShape)
        )
    }
}

@Composable
fun TextFieldDemo(
    textState: String,
    modifier: Modifier = Modifier,
    onTextChange: (String)->Unit = {}
) {
    OutlinedTextField(
        value = textState,
        onValueChange = onTextChange,
        modifier = modifier
    )
}


@Composable
fun ListDemo(listOfStrings: List<String>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(listOfStrings) { index, text->
            Text("$index $text")
        }
    }

    LazyVerticalGrid(columns = GridCells.Fixed(2)) {

    }
}



@Preview(showBackground = true)
@Composable
fun ListDemoPreview() {
    ComposeClass1Theme {
        ListDemo(
            listOfStrings = listOf(
                "abc",
                "xyz",
                "vvhjhj",
                "wckwcj",
                "rygyuegyugre",
                "cnjcnsdjkc",
                "w32d",
                "erfr",
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserInteractionPreview() {
    ComposeClass1Theme {
        UserInteraction(modifier = Modifier.fillMaxSize(),)
    }
}