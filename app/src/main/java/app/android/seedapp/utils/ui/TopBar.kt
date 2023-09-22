package app.android.seedapp.utils.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.android.seedapp.R
import app.android.seedapp.navigation.AppScreens
import app.android.seedapp.ui.theme.White
import app.android.seedapp.ui.theme.AppPrimary

@Composable
fun TopBar(
    title: String,
    showBackArrow: Boolean = false,
    showIcon: Boolean = false,
    onClickBackArrow: () -> Unit = {},
    onClickIcon: () -> Unit = {}
) {

    BackHandler(true) {
        onClickBackArrow()
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppPrimary)
            .padding(16.dp, 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {


        IconButton(
            modifier = Modifier
                .size(20.dp)
                .alpha(if (showBackArrow) 1f else 0f),
            enabled = showBackArrow,
            onClick = { onClickBackArrow() }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                tint = White,
                contentDescription = ""
            )
        }

        Spacer(modifier = Modifier.size(16.dp))


        Text(
            text = title,
            fontSize = 24.sp,
            color = White,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.size(16.dp))

        IconButton(
            modifier = Modifier
                .size(20.dp)
                .alpha(if (showIcon) 1f else 0f),
            enabled = showIcon,
            onClick = { onClickIcon() }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_logout),
                tint = White,
                contentDescription = ""
            )
        }
    }
}