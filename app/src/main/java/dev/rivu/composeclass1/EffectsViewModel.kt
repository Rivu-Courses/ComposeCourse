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