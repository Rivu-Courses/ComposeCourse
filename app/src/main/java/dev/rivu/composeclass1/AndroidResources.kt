package dev.rivu.composeclass1

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import dev.rivu.composeclass1.ui.theme.ComposeClass1Theme

@Composable
fun ResourcesScreen() {
    Column {
        Box {
            Image(
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = "BG",

            )
            Icon(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = "Icon",
                tint = Color.Unspecified
            )
        }

        Icon(
            painter = painterResource(R.drawable.rivu),
            contentDescription = "Icon",
            modifier = Modifier.size(100.dp),
            tint = Color.Unspecified
        )

        Text(
            text = stringResource(R.string.app_name)
        )

        Text(
            text = stringResource(R.string.formatted_string, "Abc", "Xyz"),
            color = colorResource(R.color.teal_700),
            fontSize = TextUnit(dimensionResource(R.dimen.text_size).value, TextUnitType.Sp)
        )

    }
}


@Preview
@Composable
fun ResourcesScreenPreview() {
    ComposeClass1Theme {
        ResourcesScreen()
    }
}