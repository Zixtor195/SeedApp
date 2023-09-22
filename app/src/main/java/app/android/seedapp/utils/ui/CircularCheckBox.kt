package app.android.seedapp.utils.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import app.android.seedapp.ui.theme.Transparent
import app.android.seedapp.ui.theme.AppPrimary

@Composable
fun CircularCheckBox(isCheck: Boolean, text: String, onSelectCheckBox: (Boolean) -> Unit) {

    Row {

        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 8.dp),
            color = AppPrimary,
            text = text,
        )

        Spacer(modifier = Modifier.size(8.dp))

        Card(
            modifier = Modifier.size(20.dp),
            elevation = 0.dp,
            shape = CircleShape,
            backgroundColor = Transparent,
            border = BorderStroke(2.5.dp, color = AppPrimary)
        ) {
            Box(
                modifier = Modifier
                    .size(18.dp)
                    .padding(4.5.dp)
                    .clip(CircleShape)
                    .background(if (isCheck) AppPrimary else Transparent)
                    .clickable {
                        onSelectCheckBox(!isCheck)
                    },
            )
        }


    }
}