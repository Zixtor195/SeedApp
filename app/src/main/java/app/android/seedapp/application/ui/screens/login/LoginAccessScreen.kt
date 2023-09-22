package app.android.seedapp.application.ui.screens.login

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import app.android.seedapp.R
import app.android.seedapp.application.ui.viewmodels.login.LoginAccessViewModel
import app.android.seedapp.navigation.AppScreens
import app.android.seedapp.ui.theme.*
import app.android.seedapp.utils.NetworkResult
import app.android.seedapp.utils.ui.CodeField
import app.android.seedapp.utils.ui.HeaderImageLogo
import app.android.seedapp.utils.ui.MessageError
import app.android.seedapp.utils.ui.PasswordField


@Composable
fun LoginAccessScreen(
    loginAccessViewModel: LoginAccessViewModel,
    navController: NavController,
    userId: String
) {

    val accessType = loginAccessViewModel.validateTypePassword.observeAsState().value
    val loginEnabled: Boolean by loginAccessViewModel.loginEnable.collectAsState()
    val showLoginError: Boolean by loginAccessViewModel.showLoginError.collectAsState()
    val authCode: String by loginAccessViewModel.authCode.collectAsState()
    val userPassword: String by loginAccessViewModel.userPassword.collectAsState()

    BackHandler(true) {
        navController.popBackStack()
    }

    LaunchedEffect(Unit) {
        loginAccessViewModel.validateTypePassword(userId)
    }


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_main),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        when (accessType) {
            is NetworkResult.Success -> {
                Log.i("LoginAccessScreen", "ValidateTypePassword Work on Success")
                accessType.data?.let {
                    if (it.requirePassword) {
                        LoginAccessPasswordScreen(
                            Modifier.align(Center),
                            authCode,
                            userPassword,
                            userId,
                            loginEnabled,
                            it.requirePassword,
                            showLoginError,
                            navController,
                            loginAccessViewModel
                        )

                    } else {
                        LoginAccessCodeScreen(
                            Modifier.align(Center),
                            authCode,
                            userPassword,
                            userId,
                            loginEnabled,
                            it.requirePassword,
                            showLoginError,
                            navController,
                            loginAccessViewModel
                        )
                    }

                }
            }

            is NetworkResult.Loading -> {
                Log.i("LoginAccessScreen", "ValidateTypePassword Work on Loading")
            }

            is NetworkResult.Error -> {
                Log.e("LoginAccessScreen", "ValidateTypePassword Work on error: " + accessType.message)
            }
        }

    }


}

@Composable
fun LoginAccessCodeScreen(
    modifier: Modifier,
    authCode: String,
    userPassword: String,
    userId: String,
    loginEnabled: Boolean,
    requirePassword: Boolean,
    showLoginError: Boolean,
    navController: NavController,
    loginAccessViewModel: LoginAccessViewModel
) {
    val context = LocalContext.current

    Card(
        modifier = modifier
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

            CodeField("Código", authCode) {
                loginAccessViewModel.onUserAuthCodeChange(it)
            }

            Spacer(modifier = Modifier.padding(10.dp))

            if (showLoginError) {
                MessageError(textError = "Ha ocurrido un error verifica el código e inténtalo de nuevo")
            }

            Spacer(modifier = Modifier.padding(25.dp))

            ClickableText(
                onClick = {
                    loginAccessViewModel.validateTypePassword(userId)
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

            LoginButton(
                authCode,
                userPassword,
                userId,
                loginEnabled,
                requirePassword,
                navController,
                loginAccessViewModel
            )
        }
    }
}

@Composable
fun LoginAccessPasswordScreen(
    modifier: Modifier,
    authCode: String,
    userPassword: String,
    userId: String,
    loginEnabled: Boolean,
    requirePassword: Boolean,
    showLoginError: Boolean,
    navController: NavController,
    loginAccessViewModel: LoginAccessViewModel,
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(35.dp),
    ) {

        HeaderImageLogo(Modifier.align(Alignment.CenterHorizontally))

        Spacer(modifier = Modifier.padding(16.dp))

        PasswordField(value = userPassword, placeHolder = "Contraseña") { loginAccessViewModel.onUserPasswordChange(it) }

        Spacer(modifier = Modifier.padding(10.dp))

        if (showLoginError) {
            MessageError(textError = "Contraseña incorrecta")
        }

        Spacer(modifier = Modifier.padding(80.dp))

        LoginButton(
            authCode,
            userPassword,
            userId,
            loginEnabled,
            requirePassword,
            navController,
            loginAccessViewModel
        )
    }
}

@Composable
fun LoginButton(
    authCode: String,
    userPassword: String,
    userId: String,
    loginEnable: Boolean,
    requirePassword: Boolean,
    navController: NavController,
    loginAccessViewModel: LoginAccessViewModel
) {
    Button(
        onClick = {
            loginAccessViewModel.loginUser(userId, authCode, userPassword, requirePassword, navController)
        },
        enabled = loginEnable,
        modifier = Modifier
            .padding(20.dp, 0.dp)
            .fillMaxWidth()
            .height(45.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = HighLightsColor,
            contentColor = White,
            disabledBackgroundColor = HighLightsDisabledColor,
            disabledContentColor = White
        )
    ) {
        Text(
            text = "Iniciar Sesion",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


