package app.android.seedapp.application.ui.screens.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import app.android.seedapp.application.ui.viewmodels.user.UserDetailsViewModel
import app.android.seedapp.application.ui.viewmodels.user.state.UserDetailsUiState
import app.android.seedapp.navigation.AppScreens
import app.android.seedapp.ui.theme.Black
import app.android.seedapp.ui.theme.DarkWhite
import app.android.seedapp.utils.ui.TopBar

@Composable
fun UserDetailsScreen(navController: NavHostController, userDetailsViewModel: UserDetailsViewModel, idKey: String) {

    val userDetailsUiState: UserDetailsUiState by userDetailsViewModel.userDetailsUiState.collectAsState()


    LaunchedEffect(Unit) {
        userDetailsViewModel.getUserReferredInfo(idKey)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkWhite)
    ) {
        Column {
            TopBar(
                title = "Información del referido",
                showBackArrow = true,
                onClickBackArrow = {
                    navController.popBackStack()
                }
            )

            Spacer(modifier = Modifier.size(16.dp))

            val name =
                "${userDetailsUiState.registerUserResponse.userFirstName} ${userDetailsUiState.registerUserResponse.userLastName}"
            UserInfoTextRow("Nombre", name)

            Spacer(modifier = Modifier.size(8.dp))

            UserInfoTextRow("Telefono", userDetailsUiState.registerUserResponse.userMobile)

            Spacer(modifier = Modifier.size(8.dp))

            UserInfoTextRow("Genero", userDetailsUiState.registerUserResponse.userGenre)

            Spacer(modifier = Modifier.size(8.dp))

            UserInfoTextRow("Numero de documento", userDetailsUiState.registerUserResponse.userNumberDocument)

            Spacer(modifier = Modifier.size(8.dp))

            UserInfoTextRow("Fecha de nacimiento", userDetailsUiState.registerUserResponse.userBirthday)

            Spacer(modifier = Modifier.size(8.dp))

            UserInfoTextRow("Lugar de votacion", userDetailsUiState.registerUserResponse.userVotePlace)

            Spacer(modifier = Modifier.size(8.dp))

            UserInfoTextRow("Direccion de votacion", userDetailsUiState.registerUserResponse.userVoteAddress)

            Spacer(modifier = Modifier.size(8.dp))

            UserInfoTextRow("Mesa de votacion", userDetailsUiState.registerUserResponse.userVoteDesk)
        }

    }
}

@Composable
fun UserInfoTextRow(infoKey: String, infoBody: String) {
    Column(
        modifier = Modifier
            .height(60.dp)
            .padding(horizontal = 16.dp)
    ) {

        Text(
            text = "$infoKey:",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Black
        )

        Text(
            text = infoBody,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Black
        )
    }
}