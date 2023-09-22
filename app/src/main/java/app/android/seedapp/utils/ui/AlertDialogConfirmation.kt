package app.android.seedapp.utils.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.android.seedapp.ui.theme.AppPrimary
import app.android.seedapp.ui.theme.Black

@Composable
fun AlertDialogConfirmation(
    title: String,
    titleBold: String = "",
    textAlign: TextAlign = TextAlign.Left,
    textConfirm: String = "Si",
    textDismiss: String = "No",
    colorConfirm: Color = AppPrimary,
    colorDismiss: Color = Black,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        modifier = Modifier.clip(RoundedCornerShape(12.dp)),
        onDismissRequest = { },

        confirmButton = {
            TextButton(onClick = { onConfirm() })
            {
                Text(
                    text = textConfirm,
                    color = colorConfirm,
                    style = TextStyle(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
                        letterSpacing = 0.sp
                    )
                )
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() })
            {
                Text(
                    text = textDismiss,
                    color = colorDismiss,
                    style = TextStyle(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
                        letterSpacing = 0.sp
                    )
                )
            }
        },
        title = {

            Column {

                if (titleBold.isNotBlank()) {
                    Text(
                        text = titleBold,
                        color = Black,
                        fontSize = 18.sp,
                        textAlign = textAlign,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = title,
                    color = Black,
                    fontSize = 18.sp,
                    textAlign = textAlign,
                    fontWeight = FontWeight.Normal
                )
            }

        }
    )
}