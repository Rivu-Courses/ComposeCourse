package dev.rivu.composeclass1

import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import dev.rivu.composeclass1.ui.AnchoredDrag
import dev.rivu.composeclass1.ui.DragText
import dev.rivu.composeclass1.ui.HomePageState
import dev.rivu.composeclass1.ui.theme.ComposeClass1Theme

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
            ComposeClass1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DragText()
                }
            }
        }

        val handler = Handler()
        handler.postDelayed({
            homePageState.topsectionState.value = "State Changed"
        }, 500)
    }
}
