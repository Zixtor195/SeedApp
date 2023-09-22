package app.android.seedapp.application.ui.screens.graphs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import app.android.seedapp.application.data.models.statistics.LeaderData
import app.android.seedapp.navigation.AppScreens
import app.android.seedapp.ui.theme.*

@Composable
fun LeadersScreen(
    navController: NavController,
    isCandidate: Boolean,
    userName: String = "",
    totalRegisters: String = "",
    dataLeaderList: ArrayList<LeaderData>
) {

    Column(Modifier.padding(horizontal = 16.dp)) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Hola $userName, hoy en total tienes $totalRegisters personas apoyandote.",
            fontSize = 20.sp,
            color = Black,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Normal
        )

        Spacer(modifier = Modifier.size(16.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Tus líderes son:",
            fontSize = 25.sp,
            color = Black,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Medium
        )

        LazyColumn(
            modifier = Modifier.fillMaxHeight(1f),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(dataLeaderList.size) {
                ItemListLeader(
                    userName = dataLeaderList[it].leaderName,
                    userRegisters = dataLeaderList[it].leaderRegisterCount ?: "0",
                    isRepeated = dataLeaderList[it].leaderIsRepeat ?: false,
                    isCandidate = isCandidate
                ) {
                    if (isCandidate) {
                        navController.navigate(
                            AppScreens.LeaderRegistersScreen.passId(
                                id = dataLeaderList[it].leaderPk,
                                name = dataLeaderList[it].leaderName,
                                repeat = dataLeaderList[it].leaderIsRepeat ?: false
                            )
                        )
                    } else {
                        navController.navigate(AppScreens.UserDetailsScreen.passId(dataLeaderList[it].leaderPk))
                    }
                }

                Spacer(modifier = Modifier.size(4.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ItemListLeader(userName: String, userRegisters: String, isRepeated: Boolean, isCandidate: Boolean, onClickItem: () -> Unit) {
    Card(backgroundColor = if (isRepeated) LightHighLightsColor else White, onClick = { onClickItem() }) {
        Row(modifier = Modifier.padding(10.dp)) {
            Text(
                modifier = Modifier.weight(1f),
                text = userName,
                fontSize = 15.sp,
                color = Black,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Normal
            )

            if (isRepeated) {
                Icon(imageVector = Icons.Default.Warning, tint = HighLightsColor, contentDescription = "")
            }

            if (isCandidate) {
                Column(Modifier.weight(0.2F)) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Inscritos",
                        fontSize = 10.sp,
                        color = Black,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Normal
                    )

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = userRegisters,
                        fontSize = 10.sp,
                        color = Black,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
        }
    }
}