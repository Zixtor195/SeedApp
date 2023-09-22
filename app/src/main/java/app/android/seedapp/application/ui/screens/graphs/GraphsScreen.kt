package app.android.seedapp.application.ui.screens.graphs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import app.android.seedapp.application.ui.viewmodels.graphs.GraphsViewModel
import app.android.seedapp.application.ui.viewmodels.graphs.state.GraphsUiState
import app.android.seedapp.ui.theme.DarkWhite
import app.android.seedapp.ui.theme.HighLightsColor
import app.android.seedapp.ui.theme.HighLightsDisabledColor
import app.android.seedapp.ui.theme.White
import app.android.seedapp.utils.ui.BarsChart
import app.android.seedapp.utils.ui.TopBar

@Composable
fun GraphsScreen(navController: NavController, graphsViewModel: GraphsViewModel) {

    val graphsUiState: GraphsUiState by graphsViewModel.graphsUiState.collectAsState()

    LaunchedEffect(Unit) {
        graphsViewModel.getUserStatistics()
        graphsViewModel.getUserLeaders()
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkWhite)
    ) {
        Column {

            TopBar(
                showBackArrow = false,
                title = "Graficas"
            )

            Spacer(modifier = Modifier.size(16.dp))

            StatisticsSelectorButton(Modifier.align(Alignment.CenterHorizontally), graphsUiState.selectedOption) {
                graphsViewModel.setSelectedOption(it)
            }

            Spacer(modifier = Modifier.size(16.dp))

            ExportToMailButton(Modifier.align(Alignment.CenterHorizontally)) { graphsViewModel.getUserCvsResponse() }

            Spacer(modifier = Modifier.size(16.dp))

            when (graphsUiState.selectedOption) {
                0 -> {
                    BarsChart(graphsUiState.data, graphsUiState.keys, graphsUiState.value, graphsUiState.maxValue)
                }

                1 -> {
                    LeadersScreen(
                        navController = navController,
                        isCandidate = graphsViewModel.getUserInfo().isCandidate,
                        userName = graphsViewModel.getUserInfo().userName,
                        totalRegisters = graphsUiState.userLeadersResponse.numberTotalUsers ?: "0",
                        dataLeaderList = graphsUiState.userLeadersResponse.dataLeaderList
                    )
                }

            }



            Spacer(modifier = Modifier.size(32.dp))
        }
    }
}

@Composable
fun StatisticsSelectorButton(modifier: Modifier, selectedOption: Int, onClickSelector: (Int) -> Unit) {

    Row(
        modifier = modifier
            .border(width = 1.dp, color = HighLightsColor, shape = RoundedCornerShape(8.dp))
            .widthIn(min = 150.dp, max = 200.dp)

    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .background(
                    if (selectedOption == 0) HighLightsColor else White,
                    shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
                )
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .clickable { onClickSelector(0) },
            text = "Gráficas",
            fontSize = 15.sp,
            color = if (selectedOption == 0) White else HighLightsColor,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium
        )

        Text(
            modifier = Modifier
                .weight(1f)
                .background(
                    if (selectedOption == 1) HighLightsColor else White,
                    shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
                )
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .clickable { onClickSelector(1) },
            text = "Líderes",
            fontSize = 15.sp,
            color = if (selectedOption == 1) White else HighLightsColor,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun ExportToMailButton(
    modifier: Modifier,
    onClickExportToMailButton: () -> Unit
) {
    Button(
        onClick = { onClickExportToMailButton() },
        modifier = modifier.height(48.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = HighLightsColor,
            contentColor = White,
            disabledBackgroundColor = HighLightsDisabledColor,
            disabledContentColor = White
        )
    ) {
        Text(text = "Exportar base de datos")
    }
}