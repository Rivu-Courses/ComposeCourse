package dev.rivu.composeclass1.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import dev.rivu.composeclass1.EffectsViewModel
import dev.rivu.composeclass1.State
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BottomSheets(
    sheetState: ModalBottomSheetState = androidx.compose.material.rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
) {

    SideEffect {

    }

    Box(modifier = Modifier.fillMaxSize()) {

        val coroutineScope = rememberCoroutineScope()
        ModalBottomSheetLayout(
            sheetState = sheetState,
            sheetContent = {
                Text(
                    "Bottom Sheet",
                    fontSize = 45.sp
                )
            },
        ) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        sheetState.show()
                    }
                }
            ) {
                Text(
                    "Show Bottom Sheet",
                )
            }
        }
    }
}

@Composable
fun Effects(
    viewModel: EffectsViewModel
) {
    val state = viewModel.state.value

    var time by remember(System.currentTimeMillis()) {
        mutableLongStateOf(System.currentTimeMillis())
    }
    var lastChecked by remember {
        mutableLongStateOf(System.currentTimeMillis())
    }


    LaunchedEffect(time) {
        delay(1000)
        time = System.currentTimeMillis()
    }

    val isValid: Boolean by remember(state, (time - lastChecked) > 1000) {
        Log.d("Effects", "key reevaluated")
        lastChecked = time

        mutableStateOf(
            state is State.Succcess && (time - state.timestamp) < state.validity
        )
    }

    DisposableEffect(isValid) {
        val job =
        if (!isValid) {
            viewModel.loadData()
        } else null

        onDispose {
            job?.cancel()
        }
    }


    when (state) {
        is State.Loading -> Text("Loading")
        is State.Succcess -> Text(state.data)
        is State.Error -> Text(state.errorDetails)
    }
}

val numbersCalled: MutableList<Int> = mutableListOf()

fun addition(n1: Int, n2: Int): Int {
    numbersCalled.add(n1)
    numbersCalled.add(n2)
    return n1+n2
}