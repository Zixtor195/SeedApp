package app.android.seedapp.application.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import app.android.seedapp.R
import app.android.seedapp.application.ui.viewmodels.login.LoginMainViewModel
import app.android.seedapp.navigation.AppScreens
import app.android.seedapp.ui.theme.Black
import app.android.seedapp.ui.theme.White
import app.android.seedapp.ui.theme.HighLightsColor
import app.android.seedapp.ui.theme.HighLightsDisabledColor
import app.android.seedapp.utils.Constants
import app.android.seedapp.utils.Constants.LOGIN_SCREEN
import app.android.seedapp.utils.Constants.LOGIN_WEB_VIEW_SCREEN
import app.android.seedapp.utils.ui.ContentView
import app.android.seedapp.utils.ui.HeaderImageLogo

@Composable
fun LoginMainScreen(
    loginMainViewModel: LoginMainViewModel,
    navController: NavController
) {

    val loginMainNavController: String by loginMainViewModel.loginMainNavController.collectAsState()
    val isLogged: Boolean by loginMainViewModel.isLogged.collectAsState()

    LaunchedEffect(Unit) {
        loginMainViewModel.getAppToken()
    }

    if (isLogged) LaunchedEffect(Unit) {
        navController.popBackStack()
        navController.navigate(AppScreens.CampaignsSelectionScreen.route)
    }

    when (loginMainNavController) {
        LOGIN_SCREEN -> {
            bodyLogin(loginMainViewModel, navController)
        }

        LOGIN_WEB_VIEW_SCREEN -> {
            ContentView(
                title = "Terminos y condiciones",
                urlToOpen = "https://docs.google.com/viewer?url=http://referidos.othelo.com.co/media/media/url_terminos/Terminos_y_Condiciones_SEEDAPP.pdf"
            ) {
                loginMainViewModel.onDisconnectWebView()
            }
        }
    }
}

@Composable
fun bodyLogin(
    loginMainViewModel: LoginMainViewModel,
    navController: NavController
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_main),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds,
        )
        Login(
            Modifier
                .align(Alignment.Center)
                .padding(16.dp),
            navController,
            loginMainViewModel
        )
    }
}

@Composable
fun Login(
    modifier: Modifier,
    navController: NavController,
    loginMainViewModel: LoginMainViewModel,
) {

    LaunchedEffect(Unit) {
        loginMainViewModel.getFirebaseToken()
    }

    val userId: String by loginMainViewModel.userId.collectAsState()
    val validationEnable: Boolean by loginMainViewModel.validationEnable.collectAsState()
    val isExpanded: Boolean by loginMainViewModel.isExpanded.collectAsState()
    val termsAcepted: Boolean by loginMainViewModel.termsAccepted.collectAsState()

    Column(modifier = modifier) {
        HeaderImageLogo(Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.padding(16.dp))

        IdentificationType(
            Modifier.align(Alignment.CenterHorizontally),
            isExpanded
        ) { loginMainViewModel.onExpandIdentificationBox(it) }

        Spacer(modifier = Modifier.padding(16.dp))

        UserIdField(userId) {
            loginMainViewModel.onUserIdChange(it, termsAcepted)
        }

        Spacer(modifier = Modifier.padding(50.dp))

        TermsAndConditions(
            Modifier.align(Alignment.CenterHorizontally),
            termsAcepted,
            onClickTerms = { loginMainViewModel.onClickShowTerms() },
            onCheckBoxChange = { loginMainViewModel.onUserIdChange(userId, it) }
        )

        Spacer(modifier = Modifier.padding(8.dp))

        ValidateLoginTypeButton(validationEnable, navController, userId)
    }


}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun IdentificationType(modifier: Modifier, isExpanded: Boolean, onExpandIdentificationBox: (Boolean) -> Unit) {

    val identificationTypeList = arrayOf("Cédula de ciudadania")
    var selectedItem by remember { mutableStateOf(identificationTypeList[0]) }

    ExposedDropdownMenuBox(
        modifier = modifier.fillMaxWidth(),
        expanded = isExpanded,
        onExpandedChange = {
            onExpandIdentificationBox(!isExpanded)
        }
    ) {
        OutlinedTextField(
            modifier = Modifier.exposedDropdownSize(),
            value = selectedItem,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = White,
                unfocusedBorderColor = White,
                trailingIconColor = White,
                textColor = White
            )
        )

        DropdownMenu(
            modifier = Modifier
                .background(White)
                .exposedDropdownSize(),
            expanded = isExpanded,
            onDismissRequest = { onExpandIdentificationBox(false) }
        ) {
            identificationTypeList.forEach { selectedOption ->
                DropdownMenuItem(
                    onClick = {
                        onExpandIdentificationBox(false)
                        selectedItem = selectedOption
                    }) {
                    Text(text = selectedOption, color = Black)
                }
            }
        }

    }


}

@Composable
fun UserIdField(userId: String, onTextFieldChange: (String) -> Unit) {
    TextField(
        value = userId,
        onValueChange = { onTextFieldChange(it) },
        placeholder = { Text(text = "Cédula", color = White) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            textColor = White,
            focusedIndicatorColor = White,
            unfocusedIndicatorColor = White,
            cursorColor = White
        )
    )
}

@Composable
fun TermsAndConditions(modifier: Modifier, isAccepted: Boolean, onClickTerms: () -> Unit, onCheckBoxChange: (Boolean) -> Unit) {


    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Aceptar términos y condiciones",
            modifier = modifier.clickable { onClickTerms() },
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = White
        )

        Checkbox(
            checked = isAccepted,
            onCheckedChange = { onCheckBoxChange(it) }
        )
    }
}

@Composable
fun ValidateLoginTypeButton(validationEnable: Boolean, navController: NavController, userId: String) {
    Button(
        onClick = {
            navController.navigate(route = AppScreens.LoginAccessScreen.passId(userId))
        },
        enabled = validationEnable,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = HighLightsColor,
            contentColor = White,
            disabledBackgroundColor = HighLightsDisabledColor,
            disabledContentColor = White
        )
    ) {
        Text(text = "Validar")
    }
}

@Composable
fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize()) { CircularProgressIndicator(modifier = Modifier.align(Alignment.Center)) }
}


