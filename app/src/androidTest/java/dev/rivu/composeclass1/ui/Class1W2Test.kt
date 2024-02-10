package dev.rivu.composeclass1.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import dev.rivu.composeclass1.ExampleViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class Class1W2Test {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        composeTestRule.setContent {
            AppHome(FakeExampleViewModel)
        }
    }

    @Test
    fun testSetup() {
        composeTestRule.onNode(hasTestTag("navBottomSheet")).isDisplayed()
    }

    @Test
    fun testNavigation() {
        composeTestRule.onNode(hasTestTag("navScreen1")).performClick()
        composeTestRule.onNode(hasTestTag("Screen1")).assertIsDisplayed()
        composeTestRule.onNode(hasTestTag("navScreen2")).performClick()
        composeTestRule.onNode(hasTestTag("Screen2")).assertIsDisplayed()
        composeTestRule.onNode(hasTestTag("Screen1")).assertDoesNotExist()
    }
}

object FakeExampleViewModel: ExampleViewModel() {
    override fun doSomething() {
        super.doSomething()
    }
}