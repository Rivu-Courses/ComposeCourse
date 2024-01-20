package dev.rivu.composeclass1

import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import dev.rivu.composeclass1.ui.HomePageState
import dev.rivu.composeclass1.ui.theme.ComposeClassTheme
import dev.rivu.composeclass1.userslist.ui.PaginatedUsersListScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val homePageState = HomePageState(
        topsectionState = mutableStateOf("Top Section"),
        leftSectionState = (1..10).toList().map {
            "Left Section Item $it"
        },
        rightSectionState = (1..10).toList().map {
            "Right Section Item $it"
        },
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val effectsViewModel = EffectsViewModel()
        setContent {
            ComposeClassTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    PaginatedUsersListScreen()
                }
            }
        }

        val handler = Handler()
        handler.postDelayed({
            homePageState.topsectionState.value = "State Changed"
        }, 500)
    }
}
