package app.android.seedapp.application.ui.screens.register

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import app.android.seedapp.application.data.models.register.DepartmentDetails
import app.android.seedapp.application.data.models.register.MunicipalityDetails
import app.android.seedapp.application.data.models.register.RegisterUserRequest
import app.android.seedapp.application.ui.viewmodels.register.RegisterUserViewModel
import app.android.seedapp.navigation.AppScreens
import app.android.seedapp.ui.theme.*
import app.android.seedapp.utils.Constants.REGISTER_USER_CODE_SCREEN
import app.android.seedapp.utils.Constants.REGISTER_USER_SCREEN
import app.android.seedapp.utils.Constants.REGISTER_USER_WEB_VIEW_SCREEN
import app.android.seedapp.utils.ui.CircularCheckBox
import app.android.seedapp.utils.ui.ContentView
import app.android.seedapp.utils.ui.TopBar
import app.android.seedapp.utils.ui.dateSelector

@Composable
fun RegisterUserScreen(navController: NavHostController, registerUserViewModel: RegisterUserViewModel) {

    val registerUserRequest: RegisterUserRequest by registerUserViewModel.registerUserRequest.collectAsState()
    val municipalityList: List<MunicipalityDetails> by registerUserViewModel.municipalityList.collectAsState()
    val departmentList: List<DepartmentDetails> by registerUserViewModel.departmentList.collectAsState()
    val municipalityListIsExpanded: Boolean by registerUserViewModel.municipalityListIsExpanded.collectAsState()
    val departmentListIsExpanded: Boolean by registerUserViewModel.departmentListIsExpanded.collectAsState()
    val continueEnable: Boolean by registerUserViewModel.continueEnable.collectAsState()
    val registerNavController: String by registerUserViewModel.registerNavController.collectAsState()
    val selectedBirthDate: String by registerUserViewModel.selectedBirthDate.collectAsState()
    val selectedDepartment: DepartmentDetails by registerUserViewModel.selectedDepartment.collectAsState()
    val selectedMunicipality: MunicipalityDetails by registerUserViewModel.selectedMunicipality.collectAsState()
    val userAge: Int by registerUserViewModel.userAge.collectAsState()

    LaunchedEffect(Unit) {
        registerUserViewModel.getUserIdInfo()
        registerUserViewModel.getActiveCandidate()
        registerUserViewModel.getDepartmentList()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkWhite)
    ) {

        when (registerNavController) {
            REGISTER_USER_SCREEN -> {
                RegisterUserBody(
                    departmentList,
                    municipalityList,
                    departmentListIsExpanded,
                    municipalityListIsExpanded,
                    selectedDepartment,
                    selectedMunicipality,
                    selectedBirthDate,
                    continueEnable,
                    userAge,
                    registerUserRequest,
                    registerUserViewModel,
                    navController
                )
            }

            REGISTER_USER_WEB_VIEW_SCREEN -> {
                ContentView {
                    registerUserViewModel.onDisconnectWebView()
                }
            }

            REGISTER_USER_CODE_SCREEN -> {
                RegisterUserCodeScreen(navController, registerUserRequest, registerUserViewModel)
            }
        }


    }
}

@Composable
fun RegisterUserBody(
    departmentList: List<DepartmentDetails>,
    municipalityList: List<MunicipalityDetails>,
    departmentListIsExpanded: Boolean,
    municipalityListIsExpanded: Boolean,
    selectedDepartment: DepartmentDetails,
    selectedMunicipality: MunicipalityDetails,
    selectedBirthDate: String,
    continueEnable: Boolean,
    userAge: Int,
    registerUserRequest: RegisterUserRequest,
    registerUserViewModel: RegisterUserViewModel,
    navController: NavHostController
) {

    var showEmailErrorMessage by remember { mutableStateOf(true) }
    var showGenderErrorMessage by remember { mutableStateOf(true) }
    var showUserFirstNameErrorMessage by remember { mutableStateOf(true) }
    var showUserLastNameErrorMessage by remember { mutableStateOf(true) }
    var showUserNumberDocumentErrorMessage by remember { mutableStateOf(true) }
    var showUserMobileErrorMessage by remember { mutableStateOf(true) }
    var showUserAddressErrorMessage by remember { mutableStateOf(true) }
    var showUserVoteAddressErrorMessage by remember { mutableStateOf(true) }
    var showUserVoteDeskErrorMessage by remember { mutableStateOf(true) }
    var showUserVotePlaceErrorMessage by remember { mutableStateOf(true) }
    var showDepartmentErrorMessage by remember { mutableStateOf(true) }
    var showMunicipalityErrorMessage by remember { mutableStateOf(true) }
    var showDateSelectorErrorMessage by remember { mutableStateOf(true) }

    showUserFirstNameErrorMessage = registerUserRequest.userFirstName.isBlank()
    showUserLastNameErrorMessage = registerUserRequest.userLastName.isBlank()
    showUserNumberDocumentErrorMessage = registerUserRequest.userNumberDocument.isBlank()
    showUserMobileErrorMessage = registerUserRequest.userMobile.isBlank()
    showUserAddressErrorMessage = registerUserRequest.userAddress.isBlank()
    showUserVoteAddressErrorMessage = registerUserRequest.userVoteAddress.isBlank()
    showUserVoteDeskErrorMessage = registerUserRequest.userVoteDesk.isBlank()
    showUserVotePlaceErrorMessage = registerUserRequest.userVotePlace.isBlank()
    showDepartmentErrorMessage = selectedDepartment.departmentId == -1
    showMunicipalityErrorMessage = selectedMunicipality.municipalityId == -1
    showDateSelectorErrorMessage = userAge < 18

    Column {
        TopBar(
            title = "Registro",
            showBackArrow = true,
            onClickBackArrow = {
                navController.popBackStack()
                navController.navigate(AppScreens.HomeScreen.route)
            }
        )

        Column(
            modifier = Modifier
                .padding(20.dp, 0.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Spacer(modifier = Modifier.padding(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                RegisterCheckBox(
                    principalText = "Género",
                    firstOptionText = "M",
                    secondOptionText = "F",
                    textError = "Debes seleccionar un genero",
                    isCheckFirst = registerUserRequest.userGenre == "M",
                    isCheckSecond = registerUserRequest.userGenre == "F",
                    showError = showGenderErrorMessage,
                    onCheckBoxChangeFirst = {
                        registerUserViewModel.registerUserRequestModifier(userGenre = "M")
                        showGenderErrorMessage = false
                    },
                    onCheckBoxChangeSecond = {
                        registerUserViewModel.registerUserRequestModifier(userGenre = "F")
                        showGenderErrorMessage = false
                    }
                )

                RegisterCheckBox(
                    principalText = "Lider",
                    firstOptionText = "Si",
                    secondOptionText = "No",
                    textError = "",
                    isCheckFirst = registerUserRequest.userIsLeader,
                    isCheckSecond = !registerUserRequest.userIsLeader,
                    showError = false,
                    onCheckBoxChangeFirst = { registerUserViewModel.registerUserRequestModifier(userIsLeader = true) },
                    onCheckBoxChangeSecond = { registerUserViewModel.registerUserRequestModifier(userIsLeader = false) }
                )
            }

            Spacer(modifier = Modifier.padding(8.dp))


            UserRegisterTextField(
                text = registerUserRequest.userFirstName,
                textError = "Debes ingresar un nombre valido",
                showError = showUserFirstNameErrorMessage,
                placeHolderText = "Nombre Completo",
                labelText = "Nombres"
            ) {
                registerUserViewModel.registerUserRequestModifier(userFirstName = it)
            }

            UserRegisterTextField(
                text = registerUserRequest.userLastName,
                textError = "Debes ingresar un apellido valido",
                showError = showUserLastNameErrorMessage,
                placeHolderText = "Apellido Completo",
                labelText = "Apellidos"
            ) {
                registerUserViewModel.registerUserRequestModifier(userLastName = it)
            }

            UserRegisterTextField(
                text = registerUserRequest.userNumberDocument,
                textError = "Debes ingresar un documento valido",
                showError = showUserNumberDocumentErrorMessage,
                placeHolderText = "Numero de documento",
                labelText = "Numero de documento",
                keyboardType = KeyboardOptions(keyboardType = KeyboardType.Number)
            ) {
                registerUserViewModel.registerUserRequestModifier(userNumberDocument = it)
            }

            UserRegisterTextField(
                text = registerUserRequest.userMobile,
                textError = "Debes ingresar un numero valido",
                showError = showUserMobileErrorMessage,
                placeHolderText = "Numero de celular",
                labelText = "Numero de celular",
                keyboardType = KeyboardOptions(keyboardType = KeyboardType.Number)
            ) {
                registerUserViewModel.registerUserRequestModifier(userMobile = it, userPhone = it)
            }

            UserRegisterTextField(
                text = registerUserRequest.userEmail,
                textError = "Debes ingresar un email valido",
                showError = showEmailErrorMessage,
                placeHolderText = "Correo electrónico",
                labelText = "Correo Electrónico",
                keyboardType = KeyboardOptions(keyboardType = KeyboardType.Email)
            ) {
                registerUserViewModel.registerUserRequestModifier(userEmail = it)
                showEmailErrorMessage = !registerUserViewModel.isValidEmail(it)
            }

            RegisterDropDown(
                labelText = "Departamento",
                isExpanded = departmentListIsExpanded,
                showError = showDepartmentErrorMessage,
                itemList = departmentList.mapTo(arrayListOf()) { it.departmentName },
                selectedItem = selectedDepartment.departmentName,
                textError = "Debes seleccionar un departamento",
                onExpandBox = { registerUserViewModel.onExpandDepartmentList(it) },
                onClickItem = { registerUserViewModel.onSelectDepartment(it) }
            )

            RegisterDropDown(
                labelText = "Municipio",
                isExpanded = municipalityListIsExpanded,
                showError = showMunicipalityErrorMessage,
                itemList = municipalityList.mapTo(arrayListOf()) { it.municipalityName },
                selectedItem = selectedMunicipality.municipalityName,
                textError = "Debes seleccionar un municipio",
                onExpandBox = { registerUserViewModel.onExpandMunicipalityList(it) },
                onClickItem = { registerUserViewModel.onSelectMunicipality(it) }
            )

            Spacer(modifier = Modifier.padding(5.dp))

            DateSelectorTextField(dateToShow = selectedBirthDate) { registerUserViewModel.onSelectBirthDate(it) }

            if (showDateSelectorErrorMessage) ErrorTextOnFillTextField("Debes seleccionar una fecha y debe ser mayor a 18 años")

            Spacer(modifier = Modifier.padding(5.dp))

            ShowWebViewButton(Modifier.align(CenterHorizontally)) {
                registerUserViewModel.onClickShowWebViewButton()
            }

            UserRegisterTextField(
                text = registerUserRequest.userAddress,
                textError = "Debes ingresar una dirección valida",
                showError = showUserAddressErrorMessage,
                placeHolderText = "Dirección de residencia",
                labelText = "Dirección de residencia"
            ) {
                registerUserViewModel.registerUserRequestModifier(userAddress = it)
            }

            UserRegisterTextField(
                text = registerUserRequest.userVoteAddress,
                textError = "Debes ingresar una dirección valida",
                showError = showUserVoteAddressErrorMessage,
                placeHolderText = "Dirección de votación",
                labelText = "Dirección de votación"
            ) {
                registerUserViewModel.registerUserRequestModifier(userVoteAddress = it)
            }

            UserRegisterTextField(
                text = registerUserRequest.userVotePlace,
                textError = "Debes ingresar un lugar de votación valido",
                showError = showUserVotePlaceErrorMessage,
                placeHolderText = "Ingresa lugar de votación",
                labelText = "Puesto de votación"
            ) {
                registerUserViewModel.registerUserRequestModifier(userVotePlace = it)
            }

            UserRegisterTextField(
                text = registerUserRequest.userVoteDesk,
                textError = "Debes ingresar una mesa de votación valido",
                showError = showUserVoteDeskErrorMessage,
                placeHolderText = "Ingresa Mesa de votación",
                labelText = "Mesa de votación",
                keyboardType = KeyboardOptions(keyboardType = KeyboardType.Number)
            ) {
                registerUserViewModel.registerUserRequestModifier(userVoteDesk = it)
            }

            Spacer(modifier = Modifier.padding(16.dp))

            ContinueRegisterUserButton(Modifier.align(CenterHorizontally), continueEnable) {
                registerUserViewModel.onClickContinueRegisterUserButton()
            }

            Spacer(modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun UserRegisterTextField(
    text: String,
    placeHolderText: String,
    labelText: String,
    textError: String = "",
    showError: Boolean = false,
    keyboardType: KeyboardOptions = KeyboardOptions.Default,
    onTextFieldChange: (String) -> Unit
) {

    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    OutlinedTextField(
        modifier = Modifier
            .focusRequester(focusRequester)
            .fillMaxWidth(),
        value = text,
        onValueChange = { onTextFieldChange(it) },
        maxLines = 1,
        singleLine = true,
        trailingIcon = { },
        placeholder = { Text(text = placeHolderText, color = AppPrimary) },
        label = { Text(text = labelText, color = AppPrimary) },
        textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
        keyboardOptions = keyboardType,
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),

        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = AppPrimary,
            unfocusedBorderColor = AppPrimary,
            trailingIconColor = AppPrimary,
            textColor = AppPrimary
        )
    )

    if (showError) ErrorTextOnFillTextField(textError)
}

@Composable
fun ErrorTextOnFillTextField(text: String) {
    Text(
        text = text,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Red
    )
}


@Composable
fun RegisterCheckBox(
    principalText: String,
    firstOptionText: String,
    secondOptionText: String,
    textError: String,
    isCheckFirst: Boolean,
    isCheckSecond: Boolean,
    showError: Boolean,
    onCheckBoxChangeFirst: (Boolean) -> Unit,
    onCheckBoxChangeSecond: (Boolean) -> Unit
) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "$principalText: ",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = AppPrimary
            )
            CircularCheckBox(isCheckFirst, firstOptionText) { onCheckBoxChangeFirst(it) }
            CircularCheckBox(isCheckSecond, secondOptionText) { onCheckBoxChangeSecond(it) }
        }

        if (showError) ErrorTextOnFillTextField(textError)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RegisterDropDown(
    labelText: String,
    isExpanded: Boolean,
    showError: Boolean,
    itemList: List<String>,
    selectedItem: String,
    textError: String,
    onExpandBox: (Boolean) -> Unit,
    onClickItem: (Int) -> Unit
) {

    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    ExposedDropdownMenuBox(
        modifier = Modifier.fillMaxWidth(),
        expanded = isExpanded,
        onExpandedChange = {
            onExpandBox(!isExpanded)
        }
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            value = selectedItem,
            onValueChange = {},
            placeholder = { Text(text = labelText, color = AppPrimary) },
            label = { Text(text = labelText, color = AppPrimary) },
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = AppPrimary,
                unfocusedBorderColor = AppPrimary,
                trailingIconColor = AppPrimary,
                textColor = AppPrimary
            )
        )

        ExposedDropdownMenu(
            modifier = Modifier
                .background(Color.White)
                .exposedDropdownSize(),
            expanded = isExpanded,
            onDismissRequest = { onExpandBox(false) }
        ) {
            itemList.forEachIndexed { index, selectedOption ->
                DropdownMenuItem(
                    onClick = {
                        onClickItem(index)
                        onExpandBox(false)
                        focusManager.clearFocus()
                    }) {
                    Text(text = selectedOption, color = AppPrimary)
                }
            }
        }
    }

    if (showError) ErrorTextOnFillTextField(textError)
}

@Composable
fun ContinueRegisterUserButton(
    modifier: Modifier,
    enabledRegister: Boolean,
    onClickContinueRegisterUserButton: () -> Unit
) {
    Button(
        onClick = { onClickContinueRegisterUserButton() },
        enabled = enabledRegister,
        modifier = modifier
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = HighLightsColor,
            contentColor = White,
            disabledBackgroundColor = HighLightsDisabledColor,
            disabledContentColor = White
        )
    ) {
        Text(text = "Siguiente")
    }
}

@Composable
fun DateSelectorTextField(
    dateToShow: String,
    onSelectDate: (String) -> Unit
) {

    var chargeNumber by remember { mutableStateOf(dateToShow) }
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                dateSelector(context, chargeNumber) {
                    chargeNumber = it
                    onSelectDate(it)
                }.show()
            },
        border = BorderStroke(1.dp, AppPrimary),
        elevation = 0.dp,
        backgroundColor = Transparent,
        shape = RoundedCornerShape(10)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = if (chargeNumber.isBlank()) {
                    "Fecha de nacimiento"
                } else {
                    dateToShow
                },
                fontSize = 16.sp,
                textAlign = TextAlign.Left,
                color = AppPrimary,
                fontWeight = FontWeight.Normal
            )

            Icon(
                imageVector = Icons.Default.DateRange,
                modifier = Modifier.padding(16.dp, 0.dp),
                tint = AppPrimary,
                contentDescription = null
            )
        }
    }

}

@Composable
fun ShowWebViewButton(modifier: Modifier, onClickShowWebViewButton: () -> Unit) {
    Button(
        onClick = { onClickShowWebViewButton() },
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = HighLightsColor,
            contentColor = White,
            disabledBackgroundColor = HighLightsDisabledColor,
            disabledContentColor = White
        )
    ) {
        Text(text = "Consultar información de votación")
    }
}