package app.android.seedapp.utils.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.AlertDialog
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.android.seedapp.R

@Composable
fun AlertDialogScreen(
    confirmText: String = stringResource(R.string.alert_dialog_default_confirm_text),
    titleText: String = stringResource(R.string.alert_dialog_default_title_text),
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { },
        title = {
            Column {
                Text(
                    text = titleText,
                    color = Color.Black,
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        letterSpacing = 0.sp
                    )
                )
                Spacer(modifier = Modifier.size(20.dp))

                Divider()
            }
        },
        confirmButton = {
            TextButton(onClick = { onDismiss() })
            {
                Text(
                    text = confirmText,
                    color = Color.Black,
                    style = TextStyle(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
                        letterSpacing = 0.sp
                    )
                )
            }
        }
    )
}