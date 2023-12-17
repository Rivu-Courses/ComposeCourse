package dev.rivu.composeclass1.ui

import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnchoredDrag() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        items(20) {
            Box(
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth()
                    .background(Color.Green)
            ) {
                Text("Item Action $it", modifier = Modifier.background(Color.Red))
                Text(
                    "Item 2nd Action $it",
                    modifier = Modifier
                        .background(Color.Red)
                        .align(Alignment.CenterEnd)
                )
                val density = LocalDensity.current
                val defaultActionSize = 100.dp
                val endActionSizePx = with(density) { (defaultActionSize * 2).toPx() }
                val startActionSizePx = with(density) { defaultActionSize.toPx() }

                val anchoredDragstate = remember {
                    AnchoredDraggableState(
                        initialValue = DragAnchors.Center,
                        anchors = DraggableAnchors {
                            DragAnchors.Center at 0f
                            DragAnchors.End at endActionSizePx
                        },
                        positionalThreshold = { distance: Float -> distance * 0.5f },
                        velocityThreshold = { with(density) { 500.dp.toPx() } },
                        animationSpec = spring(),
                    )
                }
                Box(
                    modifier = Modifier
                        .anchoredDraggable(
                            state = anchoredDragstate,
                            orientation = Orientation.Horizontal
                        )
                        .offset {
                            IntOffset(
                                x = anchoredDragstate
                                    .requireOffset()
                                    .roundToInt(),
                                y = 0,
                            )
                        }
                        .background(Color.White)
                        .fillParentMaxWidth()
                ) {
                    Text("Item $it", modifier = Modifier.align(Alignment.Center))
                }
            }
            Divider()
        }
    }
}

@Composable
fun DragText() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        var offsetX by remember {
            mutableStateOf(0f)
        }
        var offsetY by remember {
            mutableStateOf(0f)
        }

        Text(
            text = "Drag me",
            fontSize = 20.sp,
            modifier = Modifier
                .offset {
                    IntOffset(
                        x = offsetX.roundToInt(),
                        y = offsetY.roundToInt()
                    )
                }
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    }
                }
                .background(Color.Cyan)
                .padding(15.dp)
        )
    }
}

enum class DragAnchors {
    Center,
    End,
}