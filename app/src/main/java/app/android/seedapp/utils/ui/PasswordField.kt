package app.android.seedapp.utils.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import app.android.seedapp.R
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import app.android.seedapp.ui.theme.White

@Composable
fun PasswordField(
    value: String,
    placeHolder: String,
    onTextFieldChanged: (String) -> Unit
) {

    var passwordVisibility by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    val icon =
        if (passwordVisibility) {
            painterResource(id = R.drawable.ic_visibility)
        } else {
            painterResource(id = R.drawable.ic_visibility_off)
        }


    val visualTransformation =
        if (passwordVisibility) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        }

    TextField(
        value = value,
        maxLines = 1,
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = placeHolder, color = White) },
        onValueChange = { onTextFieldChanged(it) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        visualTransformation = visualTransformation,
        colors = TextFieldDefaults.textFieldColors(
            textColor = White,
            focusedIndicatorColor = White,
            unfocusedIndicatorColor = White,
            cursorColor = White
        ),

        trailingIcon = {
            IconButton(
                onClick = { passwordVisibility = !passwordVisibility }
            ) {
                Icon(painter = icon, contentDescription = "", tint = White)
            }
        },

    )
}