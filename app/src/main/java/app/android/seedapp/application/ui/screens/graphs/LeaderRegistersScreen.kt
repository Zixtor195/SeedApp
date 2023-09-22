package app.android.seedapp.application.ui.screens.graphs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import app.android.seedapp.application.ui.viewmodels.graphs.GraphsViewModel
import app.android.seedapp.application.ui.viewmodels.graphs.state.GraphsUiState
import app.android.seedapp.navigation.AppScreens
import app.android.seedapp.ui.theme.Black
import app.android.seedapp.ui.theme.DarkWhite
import app.android.seedapp.ui.theme.HighLightsColor
import app.android.seedapp.ui.theme.White
import app.android.seedapp.utils.ui.TopBar

@Composable
fun LeaderRegistersScreen(
    navController: NavHostController,
    graphsViewModel: GraphsViewModel,
    leaderId: String,
    leaderName: String,
    isRepeat: Boolean,
) {

    val graphsUiState: GraphsUiState by graphsViewModel.graphsUiState.collectAsState()

    LaunchedEffect(Unit) {
        graphsViewModel.getLeaderRegisters(leaderId)
    }

    val leaderRegistersList = graphsUiState.userLeadersResponse.dataLeaderList
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkWhite)
    ) {
        Column {

            TopBar(
                showBackArrow = true,
                title = "Registros por líder",
                onClickBackArrow = {
                    navController.popBackStack()
                }
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Row {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = leaderName,
                        fontSize = 20.sp,
                        color = Black,
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Normal
                    )

                    Text(
                        modifier = Modifier.clickable {
                            navController.navigate(AppScreens.UserDetailsScreen.passId(leaderId))
                        },
                        text = "Ver perfil",
                        fontSize = 15.sp,
                        color = Black,
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Normal
                    )
                }

                Spacer(modifier = Modifier.size(8.dp))

                if (isRepeat) {
                    RepeatLeaderWarning(leaderName)
                    Spacer(modifier = Modifier.size(8.dp))
                }

                LazyColumn(
                    modifier = Modifier.fillMaxHeight(1f),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(leaderRegistersList.size) {
                        ItemListLeaderRegister(leaderRegistersList[it].leaderName) {
                            navController.navigate(AppScreens.UserDetailsScreen.passId(leaderRegistersList[it].leaderPk))
                        }

                        Spacer(modifier = Modifier.size(4.dp))
                    }
                }
            }
        }
    }
}


@Composable
fun ItemListLeaderRegister(userName: String, onClickItem: () -> Unit) {
    Card(modifier = Modifier.clickable { onClickItem() }) {
        Row(modifier = Modifier.padding(10.dp)) {
            Text(
                modifier = Modifier.weight(1f),
                text = userName,
                fontSize = 15.sp,
                color = Black,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Normal
            )

            Icon(
                modifier = Modifier.weight(0.1F),
                imageVector = Icons.Default.KeyboardArrowRight,
                tint = Black,
                contentDescription = ""
            )
        }
    }
}

@Composable
fun RepeatLeaderWarning(leaderName: String) {
    Column(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = HighLightsColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Otro candidato a inscrito a $leaderName como lider en su equipo de trabajo.",
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                color = White
            )
        }
    }
}