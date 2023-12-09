package dev.rivu.composeclass1.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.rivu.composeclass1.CodeWithRivuScreenData
import dev.rivu.composeclass1.EffectsViewModel
import dev.rivu.composeclass1.ui.theme.TekoFamily

@Composable
fun ComplexScreen(viewModel: EffectsViewModel = viewModel()) {
    LaunchedEffect(Unit) {
        viewModel.loadScreenData()
    }

    val state by remember { viewModel.codeScreenState }
    val configuration = LocalConfiguration.current
    Column {
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Text("Landscape")
        } else {
            Text("Portrait")
        }

        when (state) {
            is CodeWithRivuScreenData.Loading -> {
                CircularProgressIndicator()
            }
            is CodeWithRivuScreenData.Success -> {

                SuccessState(state as CodeWithRivuScreenData.Success)
            }
        }
    }
}

@Composable
fun SuccessState(data: CodeWithRivuScreenData.Success) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val (brandingRef, textHelloRef, cardsRef) = createRefs()

        BrandingSection(
            modifier = Modifier.constrainAs(brandingRef) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
        )

        key(data.helloText) {
            Text(
                text = data.helloText,
                color = Color.DarkGray,
                modifier = Modifier.constrainAs(textHelloRef) {
                    top.linkTo(brandingRef.bottom, 30.dp)
                    start.linkTo(parent.start)
                }
            )
        }

        CardsSection(
            modifier = Modifier.constrainAs(cardsRef) {
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent

                top.linkTo(textHelloRef.bottom, 20.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            cardsData = data.cardData
        )
    }
}

@Composable
fun CardsSection(cardsData: List<String>, modifier: Modifier = Modifier, onItemClick: (String)->Unit = {}) {



    LazyRow(modifier = modifier) {
        items(cardsData) { data ->
            val cardModifier by remember(data) {
                derivedStateOf {
                    Modifier
                        .width(252.dp)
                        .height(134.dp)
                        .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 16.06264.dp))
                        .border(
                            color = Color.Red,
                            shape = RoundedCornerShape(size = 16.06264.dp),
                            width = 5.dp
                        )
                        .padding(5.dp)
                        .clickable {
                            onItemClick(data)
                        }
                }
            }


            Row {
                Card(
                    modifier = cardModifier
                ) {
                    Box(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(data)
                    }
                }

                Spacer(Modifier.size(10.dp))
            }
        }
    }
}

@Composable
fun BrandingSection(modifier: Modifier = Modifier) {
    ConstraintLayout(modifier = modifier) {
        val (imageRef, textCodeRef, textRivuRef) = createRefs()

        Image(
            imageVector = RivuTalksDemoIcon(),
            contentDescription = "Rivu Talks Demo Logo",
            modifier = Modifier.constrainAs(imageRef) {

                height = Dimension.fillToConstraints
                width = Dimension.wrapContent

                top.linkTo(parent.top)
                start.linkTo(parent.start)
                bottom.linkTo(textRivuRef.bottom)
            }
        )
        Text(
            text = "Code",
            fontFamily = TekoFamily,
            color = Color(color = 0xFF253057),
            fontSize = 56.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(textCodeRef) {
                top.linkTo(imageRef.top)
                start.linkTo(imageRef.end, 14.dp)
            }
        )
        Text(
            text = "With Rivu",
            fontFamily = TekoFamily,
            color = Color(color = 0xFF253057),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(textRivuRef) {
                top.linkTo(textCodeRef.bottom)
                start.linkTo(textCodeRef.start)
            }
        )
    }
}


@Composable
fun RivuTalksDemoIcon(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "RivuTalksDemoIcon",
            defaultWidth = 95.dp,
            defaultHeight = 95.dp,
            viewportWidth = 95f,
            viewportHeight = 95f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0xFF253057)),
                    fillAlpha = 1.0f,
                    stroke = null,
                    strokeAlpha = 1.0f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1.0f,
                    pathFillType = PathFillType.NonZero
                ) {
                    moveTo(7.67841f, 94.1755f)
                    curveTo(5.8513f, 93.8095f, 4.1733f, 92.9108f, 2.856f, 91.5928f)
                    curveTo(1.5387f, 90.2748f, 0.6409f, 88.5964f, 0.2758f, 86.769f)
                    curveTo(-0.025f, 85.2478f, 0.0536f, 83.676f, 0.5045f, 82.1923f)
                    curveTo(0.9555f, 80.7087f, 1.765f, 79.359f, 2.8614f, 78.2626f)
                    curveTo(3.9579f, 77.1661f, 5.3076f, 76.3566f, 6.7912f, 75.9056f)
                    curveTo(8.2748f, 75.4547f, 9.8467f, 75.3761f, 11.3679f, 75.677f)
                    curveTo(13.1945f, 76.0428f, 14.8721f, 76.9408f, 16.1893f, 78.2581f)
                    curveTo(17.5066f, 79.5754f, 18.4047f, 81.2529f, 18.7705f, 83.0795f)
                    curveTo(19.0713f, 84.6007f, 18.9928f, 86.1726f, 18.5418f, 87.6562f)
                    curveTo(18.0909f, 89.1399f, 17.2814f, 90.4895f, 16.1849f, 91.586f)
                    curveTo(15.0884f, 92.6825f, 13.7387f, 93.492f, 12.2551f, 93.9429f)
                    curveTo(10.7715f, 94.3939f, 9.1996f, 94.4764f, 7.6784f, 94.1755f)
                    close()
                    moveTo(83.2303f, 18.7849f)
                    curveTo(81.4037f, 18.4191f, 79.7261f, 17.5211f, 78.4089f, 16.2038f)
                    curveTo(77.0916f, 14.8865f, 76.1935f, 13.209f, 75.8277f, 11.3824f)
                    curveTo(75.5269f, 9.8612f, 75.6054f, 8.2893f, 76.0564f, 6.8057f)
                    curveTo(76.5073f, 5.3221f, 77.3168f, 3.9724f, 78.4133f, 2.8759f)
                    curveTo(79.5098f, 1.7794f, 80.8595f, 0.9699f, 82.3431f, 0.519f)
                    curveTo(83.8267f, 0.068f, 85.3986f, -0.0105f, 86.9198f, 0.2903f)
                    curveTo(88.7464f, 0.6561f, 90.424f, 1.5542f, 91.7412f, 2.8714f)
                    curveTo(93.0585f, 4.1887f, 93.9566f, 5.8663f, 94.3223f, 7.6929f)
                    curveTo(94.6232f, 9.2141f, 94.5446f, 10.7859f, 94.0937f, 12.2696f)
                    curveTo(93.6427f, 13.7532f, 92.8332f, 15.1029f, 91.7368f, 16.1994f)
                    curveTo(90.6403f, 17.2958f, 89.2906f, 18.1053f, 87.807f, 18.5563f)
                    curveTo(86.3233f, 19.0072f, 84.7515f, 19.0858f, 83.2303f, 18.7849f)
                    close()
                    moveTo(47.3817f, 23.6702f)
                    curveTo(51.8933f, 23.6702f, 56.1098f, 24.9446f, 59.6931f, 27.1433f)
                    lineTo(69.8608f, 16.9756f)
                    curveTo(62.6012f, 11.5785f, 53.6427f, 8.9723f, 44.6209f, 9.6326f)
                    curveTo(35.599f, 10.2928f, 27.1154f, 14.1756f, 20.7189f, 20.5721f)
                    curveTo(14.3224f, 26.9686f, 10.4396f, 35.4522f, 9.7794f, 44.474f)
                    curveTo(9.1191f, 53.4959f, 11.7254f, 62.4544f, 17.1224f, 69.714f)
                    lineTo(27.2941f, 59.5424f)
                    curveTo(25.1056f, 55.9715f, 23.9085f, 51.8818f, 23.8261f, 47.6945f)
                    curveTo(23.7437f, 43.5072f, 24.779f, 39.3736f, 26.8252f, 35.7194f)
                    curveTo(28.8715f, 32.0652f, 31.8549f, 29.0225f, 35.468f, 26.9046f)
                    curveTo(39.0812f, 24.7868f, 43.1936f, 23.6703f, 47.3817f, 23.6702f)
                    close()
                    moveTo(67.4693f, 34.9196f)
                    curveTo(70.2342f, 39.4319f, 71.3995f, 44.7436f, 70.7774f, 49.999f)
                    curveTo(70.1553f, 55.2543f, 67.782f, 60.1472f, 64.04f, 63.8892f)
                    curveTo(60.2979f, 67.6312f, 55.4051f, 70.0045f, 50.1497f, 70.6266f)
                    curveTo(44.8944f, 71.2487f, 39.5826f, 70.0835f, 35.0703f, 67.3186f)
                    lineTo(24.8986f, 77.4863f)
                    curveTo(32.1583f, 82.8834f, 41.1167f, 85.4896f, 50.1386f, 84.8294f)
                    curveTo(59.1605f, 84.1691f, 67.644f, 80.2863f, 74.0405f, 73.8898f)
                    curveTo(80.437f, 67.4933f, 84.3198f, 59.0097f, 84.9801f, 49.9879f)
                    curveTo(85.6404f, 40.966f, 83.0341f, 32.0075f, 77.6371f, 24.7479f)
                    lineTo(67.4693f, 34.9196f)
                    close()
                }
            }
        }.build()
    }
}

