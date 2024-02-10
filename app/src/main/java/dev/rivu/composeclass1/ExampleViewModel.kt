package dev.rivu.composeclass1

import android.os.Parcelable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.parcelize.Parcelize

open class ExampleViewModel: ViewModel() {

    val state: MutableState<ViewModelState> = mutableStateOf(
        ViewModelState(
            text = "",
            state = false
        )
    )

    open fun doSomething() {
        state.value = ViewModelState(
            text = "Somthing",
            state = true
        )
    }
}


@Parcelize
data class ViewModelState(
    val text: String,
    val state: Boolean
): Parcelable