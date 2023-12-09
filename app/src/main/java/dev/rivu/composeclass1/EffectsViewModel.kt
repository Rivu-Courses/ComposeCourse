package dev.rivu.composeclass1

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EffectsViewModel: ViewModel() {
    val state = mutableStateOf<State>(State.Loading)

    val codeScreenState = mutableStateOf<CodeWithRivuScreenData>(CodeWithRivuScreenData.Loading)

    fun loadData(): Job {
            Log.d("Effects", "loadData called")
        state.value = State.Loading
        return viewModelScope.launch {
            delay(2000)
            state.value = State.Succcess(
                timestamp = System.currentTimeMillis(),
                validity = 10000
            )
        }
    }

    fun loadScreenData() {
        viewModelScope.launch {
            delay(1000)
            codeScreenState.value = CodeWithRivuScreenData.Success(
                helloText = "Hi, I’m Rivu Chakraborty! \uD83D\uDC4B",
                cardData = listOf(
                    "Card 1",
                    "Card 2",
                    "Card 3",
                )
            )

            delay(10000)
            codeScreenState.value = CodeWithRivuScreenData.Success(
                helloText = "bcdhjsbhjbsdc Hi, I’m Rivu Chakraborty! \uD83D\uDC4B",
                cardData = listOf(
                    "Cards 1",
                    "Cards 2",
                    "Cards 3",
                    "Cards 4",
                )
            )
        }
    }
}

sealed class State {
    object Loading: State()

    @Immutable
    data class Succcess(
        val data: String = "Loaded Data",
        val timestamp: Long,
        val validity: Long,
    ): State()

    @Immutable
    data class Error(
        val errorDetails: String
    ): State()
}

sealed class CodeWithRivuScreenData {
    object Loading: CodeWithRivuScreenData()

    data class Success(
        val helloText: String,
        val cardData: List<String>
    ): CodeWithRivuScreenData()
}