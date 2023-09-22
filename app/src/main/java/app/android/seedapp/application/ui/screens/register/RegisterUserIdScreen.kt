package app.android.seedapp.application.ui.screens.register

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import app.android.seedapp.R
import app.android.seedapp.application.data.models.registryoffice.GetUserInfoFromIdResponse
import app.android.seedapp.application.ui.viewmodels.register.RegisterUserIdViewModel
import app.android.seedapp.navigation.AppScreens
import app.android.seedapp.ui.theme.Black
import app.android.seedapp.ui.theme.HighLightsColor
import app.android.seedapp.ui.theme.HighLightsDisabledColor
import app.android.seedapp.ui.theme.White
import app.android.seedapp.utils.NetworkResult
import app.android.seedapp.utils.ui.CodeField
import app.android.seedapp.utils.ui.GenericLoadingScreen
import app.android.seedapp.utils.ui.MessageError
import app.android.seedapp.utils.ui.TopBar

@Composable
fun RegisterUserIdScreen(navController: NavController, registerUserIdViewModel: RegisterUserIdViewModel) {

    /** services responses listeners begin **/

    val getUserInfoFromId = registerUserIdViewModel.getUserInfoFromId.observeAsState().value

    /** services responses listeners end **/

    val numberDocument: String by registerUserIdViewModel.numberDocument.collectAsState()
    val showCodeError = registerUserIdViewModel.showCodeError.collectAsState().value



    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        RegisterUserIdBody(registerUserIdViewModel, navController, getUserInfoFromId)

        TopBar(
            title = "Registro",
            showBackArrow = true,
            onClickBackArrow = {
                navController.popBackStack()
                navController.navigate(AppScreens.HomeScreen.route)
            }
        )

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.background_main),
                contentDescription = "Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )

            Card(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(20.dp),
                shape = RoundedCornerShape(20.dp),
                backgroundColor = White,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(35.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "Ingresa el numero de cedula que quieres registrar.",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Light,
                        color = Black
                    )

                    Spacer(modifier = Modifier.padding(25.dp))

                    CodeField("Cedula", numberDocument) {
                        registerUserIdViewModel.setUserId(id = it)
                    }

                    Spacer(modifier = Modifier.padding(10.dp))

                    if (showCodeError) {
                        MessageError(textError = "Ha ocurrido un error verifica la cedula e inténtalo de nuevo")
                    }

                    Spacer(modifier = Modifier.padding(25.dp))

                    Spacer(modifier = Modifier.padding(16.dp))

                    RegisterIdButton(numberDocument.isNotBlank()) {
                        registerUserIdViewModel.getUserInfoFromId(numberDocument)
                    }
                }
            }

        }
    }

}

@Composable
fun RegisterUserIdBody(
    registerUserIdViewModel: RegisterUserIdViewModel,
    navController: NavController,
    getUserInfoFromId: NetworkResult<GetUserInfoFromIdResponse>?
) {
    when (getUserInfoFromId) {
        is NetworkResult.Success -> {
            Log.i("LoginAccessScreen", "ValidateTypePassword Work on Success")
            getUserInfoFromId.data?.let {
                LaunchedEffect(Unit) {
                    registerUserIdViewModel.saveUserIdInfo(it)
                    registerUserIdViewModel.setShowCodeError(false)
                    navController.popBackStack()
                    navController.navigate(AppScreens.RegisterUserScreen.route)
                }
            }
        }

        is NetworkResult.Loading -> {
            Log.i("LoginAccessScreen", "ValidateTypePassword Work on Loading")
            GenericLoadingScreen()
        }

        is NetworkResult.Error -> {
            Log.e("LoginAccessScreen", "ValidateTypePassword Work on error: " + getUserInfoFromId.message)
            registerUserIdViewModel.setShowCodeError(true)
            navController.popBackStack()
            navController.navigate(AppScreens.RegisterUserScreen.route)
        }
    }
}

@Composable
fun RegisterIdButton(
    enableButton: Boolean,
    onClickContinueRegisterUserButton: () -> Unit
) {
    Button(
        onClick = { onClickContinueRegisterUserButton() },
        modifier = Modifier.height(48.dp),
        enabled = enableButton,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = HighLightsColor,
            contentColor = White,
            disabledBackgroundColor = HighLightsDisabledColor,
            disabledContentColor = White
        )
    ) {
        Text(text = "Registrar Usuario")
    }
}