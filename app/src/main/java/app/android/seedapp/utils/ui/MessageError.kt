package app.android.seedapp.utils.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.android.seedapp.R


@Composable
fun MessageError(
    textError: String,
    errorColor: Color = Red
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            modifier = Modifier.padding(4.dp, 0.dp, 4.dp, 0.dp),
            painter = painterResource(R.drawable.ic_triangle_exclamation_mark),
            colorFilter = ColorFilter.tint(errorColor),
            contentDescription = "",
        )
        Text(
            text = textError,
            color = errorColor,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                letterSpacing = 0.sp
            ),
            modifier = Modifier.padding(start = 7.dp),
        )
    }
}