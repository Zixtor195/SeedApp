package app.android.seedapp.application.ui.screens.home

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import app.android.seedapp.R
import app.android.seedapp.application.data.models.campaigns.GetCandidatesResponse
import app.android.seedapp.application.data.models.user.ReferredUserData
import app.android.seedapp.application.ui.viewmodels.home.HomeViewModel
import app.android.seedapp.application.ui.viewmodels.home.state.HomeUiState
import app.android.seedapp.navigation.AppScreens
import app.android.seedapp.ui.theme.*
import app.android.seedapp.utils.ui.AlertDialogConfirmation
import app.android.seedapp.utils.ui.TopBar
import app.android.seedapp.utils.ui.UserCardListItem


@Composable
fun HomeScreen(navController: NavController, homeViewModel: HomeViewModel) {

    val homeUiState: HomeUiState by homeViewModel.homeUiState.collectAsState()

    LaunchedEffect(Unit) {
        homeViewModel.getUserInfo()
        homeViewModel.getActiveCandidateInfo()
        homeViewModel.getUserReferredList()
    }

    if (homeUiState.showDialogLogout) {
        AlertDialogConfirmation(
            title = "¿Estas seguro que quieres cerrar sesión?",
            onDismiss = { homeViewModel.setShowDialogLogout(false) },
            onConfirm = {
                homeViewModel.closeSession()
                navController.popBackStack()
                navController.navigate(AppScreens.LoginScreen.route)
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkWhite)
    ) {
        Column {
            TopBar(
                title = "Registro",
                showIcon = true,
                onClickIcon = { homeViewModel.setShowDialogLogout(true) }
            )

            Spacer(modifier = Modifier.size(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {

                Spacer(modifier = Modifier.size(16.dp))

                if (!homeUiState.userInfo.isCandidate) {
                    ActualCandidate(
                        modifier = Modifier.weight(1f),
                        activeCandidate = homeUiState.activeCandidateInfo,
                    ) {
                        navController.popBackStack()
                        navController.navigate(AppScreens.CampaignsSelectionScreen.route)
                    }
                }

                Spacer(modifier = Modifier.size(16.dp))

            }

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                text = "Se han registrado las siguientes personas:",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Black
            )

            ReferredUserList(homeUiState.userReferredList, navController)

        }

        AddUserButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(0.dp, 0.dp, 20.dp, 70.dp),
            navController = navController
        )
    }
}

@Composable
fun ReferredUserList(userReferredList: List<ReferredUserData>, navController: NavController) {
    LazyColumn(
        modifier = Modifier.fillMaxHeight(1f),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 60.dp)
    ) {
        items(userReferredList.size) {
            UserCardListItem("${userReferredList[it].userFirstName} ${userReferredList[it].userLastName}") {
                navController.navigate(AppScreens.UserDetailsScreen.passId(userReferredList[it].primaryKey))
            }
        }
    }
}

@Composable
fun ActualCandidate(
    modifier: Modifier,
    activeCandidate: GetCandidatesResponse,
    onClickCandidateCard: () -> Unit
) {

    Column(
        modifier = modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = HighLightsColor)
            .clickable { onClickCandidateCard() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 12.dp, end = 16.dp)
        ) {
            Text(
                text = "Candidato Activo",
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
                color = White
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, bottom = 12.dp, end = 16.dp)
        ) {
            Text(
                text = activeCandidate.candidateName,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = White
            )
        }
    }

}


@Composable
fun AddUserButton(modifier: Modifier, navController: NavController) {
    Button(
        onClick = { navController.navigate(AppScreens.RegisterUserIdScreen.route) },
        modifier = modifier.size(45.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = HighLightsColor,
            contentColor = Color.White
        )
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_add),
            contentDescription = "add ic",
            tint = Color.White
        )
    }
}




