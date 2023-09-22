package app.android.seedapp.utils.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import app.android.seedapp.application.data.models.user.ReferredUserData

@Composable
fun UserCardListItem(name: String, onClickCard: () -> Unit) {
    Log.e("REGISTROS",name)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp, bottom = 4.dp)
            .clickable { onClickCard() }
    ) {
        Text(
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp, end = 16.dp, start = 16.dp),
            text = name
        )
    }
}