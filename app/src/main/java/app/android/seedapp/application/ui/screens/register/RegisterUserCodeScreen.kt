package app.android.seedapp.application.ui.screens.register

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import app.android.seedapp.R
import app.android.seedapp.application.data.models.register.RegisterUserRequest
import app.android.seedapp.application.ui.viewmodels.register.RegisterUserViewModel
import app.android.seedapp.navigation.AppScreens
import app.android.seedapp.ui.theme.*
import app.android.seedapp.utils.ui.CodeField
import app.android.seedapp.utils.ui.MessageError
import app.android.seedapp.utils.ui.TopBar

@Composable
fun RegisterUserCodeScreen(
    navController: NavHostController,
    registerUserRequest: RegisterUserRequest,
    registerUserViewModel: RegisterUserViewModel
) {
    val context = LocalContext.current
    val showCodeError = registerUserViewModel.showCodeError.collectAsState().value

    LaunchedEffect(Unit) {
        registerUserViewModel.sendConditionCodeToUser()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
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
                    .align(Center)
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
                        text = "Código de seguridad",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Black
                    )

                    Spacer(modifier = Modifier.padding(16.dp))

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "Ingresa el código que recibiste a tu correo o celular.",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Light,
                        color = Black
                    )

                    Spacer(modifier = Modifier.padding(25.dp))

                    CodeField("Código", registerUserRequest.userCodeConditions) {
                        registerUserViewModel.registerUserRequestModifier(userCodeConditions = it)
                    }

                    Spacer(modifier = Modifier.padding(10.dp))

                    if (showCodeError) {
                        MessageError(textError = "Ha ocurrido un error verifica el código e inténtalo de nuevo")
                    }

                    Spacer(modifier = Modifier.padding(25.dp))

                    ClickableText(
                        onClick = {
                            registerUserViewModel.sendConditionCodeToUser()
                            Toast.makeText(context, "Código enviado correctamente", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        style = TextStyle(textAlign = TextAlign.Center),
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = Black,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Light
                                )
                            ) {
                                append("Solicita de nuevo un código ")
                            }
                            withStyle(
                                style = SpanStyle(
                                    color = AppPrimary,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Light,
                                    textDecoration = TextDecoration.Underline
                                )
                            ) {
                                append("aquí")
                            }
                        }
                    )

                    Spacer(modifier = Modifier.padding(16.dp))

                    RegisterUserButton(true) { registerUserViewModel.registerUser(navController) }
                }
            }

        }
    }
}

@Composable
fun RegisterUserButton(
    enabledRegister: Boolean,
    onClickContinueRegisterUserButton: () -> Unit
) {
    Button(
        onClick = { onClickContinueRegisterUserButton() },
        modifier = Modifier
            .height(48.dp),
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