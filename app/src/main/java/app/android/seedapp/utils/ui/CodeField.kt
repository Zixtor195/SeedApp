package app.android.seedapp.utils.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import app.android.seedapp.ui.theme.LightBlack
import app.android.seedapp.ui.theme.Transparent
import app.android.seedapp.ui.theme.AppPrimary

@Composable
fun CodeField(title: String, authCode: String, onTextFieldChange: (String) -> Unit) {

    TextField(
        value = authCode,
        onValueChange = { onTextFieldChange(it) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        maxLines = 1,
        textStyle =
        LocalTextStyle.current.copy(
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        ),
        colors =
        TextFieldDefaults.textFieldColors(
            backgroundColor = Transparent,
            textColor = AppPrimary,
            focusedIndicatorColor = AppPrimary,
            unfocusedIndicatorColor = AppPrimary,
            cursorColor = AppPrimary
        ),
        placeholder = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                color = LightBlack,
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
        }
    )
}
