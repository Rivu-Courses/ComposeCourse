package dev.rivu.composeclass1

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class ComposeTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun sampleTest() {
        composeTestRule.setContent {
            var isClicked: Boolean by remember {
                mutableStateOf(false)
            }

            Button(
                onClick = {
                    isClicked = !isClicked
                },
                modifier = Modifier.semantics {
                    testTag = "btn"
                }
            ) {
                Text("Click Me")
            }

            if (isClicked) {
                Text("Shown")
            }
        }

        composeTestRule.onNode(hasTestTag("btn")).performClick()
        composeTestRule.onNode(hasText("Shown")).assertIsDisplayed()
        composeTestRule.onNode(hasTestTag("btn")).performClick()
        composeTestRule.onNode(hasText("Shown")).assertDoesNotExist()
    }
}