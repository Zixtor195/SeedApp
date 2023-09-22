package app.android.seedapp.utils.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import app.android.seedapp.ui.theme.HighLightsColor

@Composable
fun GenericLoadingScreen(color: Color = HighLightsColor) {
    Dialog(properties = DialogProperties(usePlatformDefaultWidth = false), onDismissRequest = { }) {
        CircularProgressIndicator(modifier = Modifier.size(100.dp), color = color)
    }
}