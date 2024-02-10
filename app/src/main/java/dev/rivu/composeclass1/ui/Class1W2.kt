package dev.rivu.composeclass1.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.rivu.composeclass1.ExampleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppHome(
    viewModel: ExampleViewModel = viewModel()
) {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Compose Week 2")
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Blue),
                actions = {

                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Screen 1", modifier = Modifier
                        .clickable {
                            navController.navigate("screen1")
                        }
                        .semantics {
                            testTag = "navScreen1"
                        }
                    )
                    Text("Screen 2", modifier = Modifier
                        .clickable {
                            navController.navigate("screen2?parameter=Abc")
                        }
                        .semantics {
                            testTag = "navScreen2"
                        }
                    )
                    Text("Bottom Sheet", modifier = Modifier
                        .clickable {
                            navController.navigate("bottomBar")
                        }
                        .semantics {
                            testTag = "navScreenBottomSheet"
                        }
                    )
                    Text("User Interaction", modifier = Modifier
                        .clickable {
                            navController.navigate("userInteraction")
                        }
                        .semantics {
                            testTag = "navScreenUI"
                        }
                    )

                }
            }
        }
    ) { paddingValues ->
        AppNavigation(
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppNavigation(modifier: Modifier, navController: NavHostController) {
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val coroutineScope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        sheetState = sheetState,
        modifier = modifier.semantics {
            testTag = "navBottomSheet"
        },
        sheetContent = {
            Text(
                "Bottom Sheet",
                fontSize = 45.sp
            )
        },
    ) {
        NavHost(navController = navController, startDestination = "screen1") {
            composable("screen1") {
                Screen1(modifier = modifier, sheetState = sheetState)
            }
            composable("screen2?parameter={parameter}",
                arguments = listOf(
                    navArgument("parameter") {
                        NavType.StringType
                    }
                )
            ) { backstackEntry ->
                Screen2(
                    modifier = modifier,
                    parameter = backstackEntry.arguments?.getString("parameter")
                )
            }
            composable("screen3") {
                Screen3(modifier = modifier)
            }
            composable("userInteraction") {
                UserInteraction(modifier, navController)
            }
            composable("bottomBar") {
                LaunchedEffect(Unit) {
                    sheetState.show()
                }
            }
        }
    }

}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Screen1(
    modifier: Modifier,
    sheetState: ModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
) {
    Column(
        modifier = modifier.background(Color.Green)
            .semantics {
                testTag = "Screen1"
            }
    ) {
        Text("Screen 1", color = Color.DarkGray)
    }

}

@Composable
fun Screen2(modifier: Modifier, parameter: String?) {
    Column(
        modifier = modifier.background(Color.Blue).semantics {
            testTag = "Screen2"
        }
    ) {
        Text("Screen 2 ${parameter ?: ""}")
    }
}

@Composable
fun Screen3(modifier: Modifier) {
    Column(
        modifier = modifier.background(Color.Red).semantics {
            testTag = "Screen3"
        }
    ) {
        Text("Screen 3")
    }
}