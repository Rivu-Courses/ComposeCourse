package dev.rivu.composeclass1.ui

import android.graphics.BitmapFactory
import android.widget.Space
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.rivu.composeclass1.R

@Composable
fun ModifierDraw() {
    Column {
        var pointerOffsetLineStart by remember {
            mutableStateOf(Offset(0f, 0f))
        }
        var pointerOffsetLineEnd by remember {
            mutableStateOf(Offset(0f, 0f))
        }
        var centerPointerOffset by remember {
            mutableStateOf(Offset(0f, 0f))
        }
        Text(
            text = "Hello Compose Drawing",
            fontSize = 30.sp,
            color = Color.White,
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        1F to Color(0xFFA3D6B5),
                        1F to Color(0xFFC2A5F4)
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
                .onSizeChanged {
                    pointerOffsetLineEnd = Offset(it.width * 1f, it.height / 2f)
                    pointerOffsetLineStart = Offset(0f, it.height / 2f)
                    centerPointerOffset = Offset(it.width / 2f, it.height / 2f)
                }
                .drawWithCache {
                    val brushRect = Brush.linearGradient(
                        1F to Color(0xFFA3D6B5),
                        1F to Color(0xFFC2A5F4)
                    )
                    val brushCircle = Brush.linearGradient(
                        1F to Color(0xFFC616D5),
                        1F to Color(0xFFF2A5F4)
                    )
                    val brushOval = Brush.linearGradient(
                        0.1F to Color(0x111016D5),
                        0.1F to Color(0x1132A5F4)
                    )
                    onDrawBehind {
                        drawRoundRect(
                            brush = brushRect,
                            cornerRadius = CornerRadius(12.dp.toPx())
                        )
                    }
                    onDrawWithContent {

                        drawCircle(
                            brush = brushCircle,
                            center = centerPointerOffset
                        )
                        drawContent()
                        drawLine(
                            color = Color.Red,
                            start = pointerOffsetLineStart,
                            end = pointerOffsetLineEnd,
                            strokeWidth = 2.dp.toPx()
                        )
                    }
                }

                .padding(6.dp)
        )

        Spacer(modifier = Modifier.size(10.dp))
        Image(painter = painterResource(id = R.drawable.rivu),
            contentDescription = "Rivu",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(320.dp)
                .aspectRatio(1f)
                .background(
                    Brush.linearGradient(
                        listOf(
                            Color(0xFFC5E1A5),
                            Color(0xFF80DEEA),
                            Color(0xFF70FCAE)
                        )
                    )
                )
                .padding(18.dp)
                .graphicsLayer {
                    compositingStrategy = CompositingStrategy.Offscreen
                }
                .drawWithCache {
                    val path = Path()
                    path.addOval(
                        Rect(
                            topLeft = Offset.Zero,
                            bottomRight = Offset(size.width, size.height)
                        )
                    )
                    onDrawWithContent {
                        clipPath(path) {
                            // this draws the actual image - if you don't call drawContent, it wont
                            // render anything
                            this@onDrawWithContent.drawContent()
                        }
                        val dotSize = size.width / 8f
                        // Clip a white border for the content
                        drawCircle(
                            Color.Blue,
                            radius = dotSize,
                            center = Offset(
                                x = size.width - dotSize,
                                y = size.height - dotSize
                            ),
                            blendMode = BlendMode.Clear
                        )
                        // draw the red circle indication
                        drawCircle(
                            Color(0xFFFF0000), radius = dotSize * 0.8f,
                            center = Offset(
                                x = size.width - dotSize,
                                y = size.height - dotSize
                            )
                        )
                    }

                }
        )

        var pointerOffset by remember {
            mutableStateOf(Offset(0f, 0f))
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput("dragging") {
                    detectDragGestures { change, dragAmount ->
                        pointerOffset += dragAmount
                    }
                }
                .onSizeChanged {
                    pointerOffset = Offset(it.width / 2f, it.height / 2f)
                }
                .drawWithContent {
                    drawContent()
                    // draws a fully black area with a small keyhole at pointerOffset that’ll show part of the UI.
                    drawRect(
                        Brush.radialGradient(
                            listOf(Color.Blue, Color.White),
                            center = pointerOffset,
                            radius = 70.dp.toPx(),
                        )
                    )
                }
        ) {
            // Your composables here
        }

    }
}

@Composable
fun CanvasDraw(modifier: Modifier = Modifier) {
    val colors by remember {
        derivedStateOf {
            listOf(Color.Yellow, Color.Red, Color.Magenta)
        }
    }
    Box(modifier = modifier) {

        Canvas(modifier = Modifier
            .size(150.dp)
            .padding(18.dp)) {
            drawRoundRect(
                brush = Brush.linearGradient(colors = colors),
                cornerRadius = CornerRadius(60f, 60f),
                style = Stroke(width = 15f, cap = StrokeCap.Round)
            )
            drawCircle(
                brush = Brush.linearGradient(colors = colors),
                radius = 45f,
                style = Stroke(width = 15f, cap = StrokeCap.Round)
            )
            drawCircle(
                brush = Brush.linearGradient(colors = colors),
                radius = 13f,
                center = Offset(this.size.width * .80f, this.size.height * 0.20f),
            )
        }
    }
}