package dev.rivu.composeclass1.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.rivu.composeclass1.ExampleViewModel
import dev.rivu.composeclass1.ViewModelState
import dev.rivu.composeclass1.ui.theme.ComposeClass1Theme
import kotlinx.coroutines.delay

@Composable
fun ViewModelScreen(viewModelExample: ExampleViewModel = viewModel()) {
    val state: ViewModelState by rememberSaveable {
        viewModelExample.state
    }

    ShowTextAndButton(
        state = state,
        onClick = {
            viewModelExample.doSomething()
        }
    )
}

@Composable
fun ShowTextAndButton(
    state: ViewModelState,
    onClick: ()->Unit
) {
    Column {
        Text("State is ${state}")
        Button(
            onClick = onClick
        ) {
            Text("Do Something")
        }
    }
}

@Preview
@Composable
fun ViewModelScreenPreview() {
    ComposeClass1Theme {
        ViewModelScreen()
    }
}