package app.android.seedapp.application.ui.screens.campaigns

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import app.android.seedapp.R
import app.android.seedapp.application.data.models.campaigns.GetCandidatesResponse
import app.android.seedapp.application.data.models.campaigns.GetUserCampaignsResponse
import app.android.seedapp.application.ui.viewmodels.campaigns.CampaignsSelectionViewModel
import app.android.seedapp.application.ui.viewmodels.campaigns.state.CampaignsSelectionUiState
import app.android.seedapp.navigation.AppScreens
import app.android.seedapp.ui.theme.Transparent
import app.android.seedapp.ui.theme.White
import app.android.seedapp.ui.theme.HighLightsColor
import app.android.seedapp.ui.theme.HighLightsDisabledColor
import app.android.seedapp.utils.NetworkResult


@Composable
fun CampaignSelectionScreen(
    navController: NavController,
    campaignsSelectionViewModel: CampaignsSelectionViewModel
) {
    val campaignsSelectionUiState: CampaignsSelectionUiState by campaignsSelectionViewModel.campaignsSelectionUiState.collectAsState()

    BackHandler(true) {
        navController.popBackStack()
    }

    LaunchedEffect(Unit) {
        campaignsSelectionViewModel.getUserCampaigns()
    }

    if (campaignsSelectionUiState.unauthorizedUser) LaunchedEffect(Unit) {
        navController.popBackStack()
        navController.navigate(AppScreens.LoginScreen.route)
    }
    else if (campaignsSelectionUiState.isCandidate) LaunchedEffect(Unit) {
        navController.popBackStack()
        navController.navigate(route = AppScreens.HomeScreen.route)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_main),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        if (campaignsSelectionUiState.showCampaignsSelector) {
            CampaignSelector(
                navController,
                campaignsSelectionUiState,
                campaignsSelectionViewModel
            )
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CampaignSelector(
    navController: NavController,
    campaignsSelectionUiState: CampaignsSelectionUiState,
    campaignsSelectionViewModel: CampaignsSelectionViewModel
) {
    val candidatesSelectorIsExpanded: Boolean by campaignsSelectionViewModel.candidatesSelectorIsExpanded.collectAsState()
    var selectedItem by remember { mutableStateOf(GetUserCampaignsResponse()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 0.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.weight(0.8f))

        Text(
            text = "Selecciona una campaña",
            fontSize = 18.sp,
            color = White,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.size(10.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            ExposedDropdownMenuBox(
                modifier = Modifier
                    .height(55.dp)
                    .fillMaxWidth(),
                expanded = campaignsSelectionUiState.campaignSelectorIsExpanded,
                onExpandedChange = {
                    campaignsSelectionViewModel.onExpandCampaignsSelector(
                        !campaignsSelectionUiState.campaignSelectorIsExpanded,
                        selectedItem
                    )
                }
            ) {
                TextField(
                    modifier = Modifier.exposedDropdownSize(),
                    shape = RoundedCornerShape(5.dp),
                    value = selectedItem.campaignDescription,
                    onValueChange = { },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = campaignsSelectionUiState.campaignSelectorIsExpanded) },
                    placeholder = {
                        Text(
                            text = "Campaña",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = White
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = HighLightsColor,
                        focusedIndicatorColor = Transparent,
                        unfocusedIndicatorColor = Transparent,
                        trailingIconColor = White,
                        textColor = White
                    )
                )

                DropdownMenu(
                    modifier = Modifier
                        .background(HighLightsColor)
                        .exposedDropdownSize(),
                    expanded = campaignsSelectionUiState.campaignSelectorIsExpanded,
                    onDismissRequest = { campaignsSelectionViewModel.onExpandCampaignsSelector(false, selectedItem) }
                ) {
                    campaignsSelectionUiState.campaignsList.forEach { selectedOption ->
                        DropdownMenuItem(
                            onClick = {
                                selectedItem = selectedOption
                                campaignsSelectionViewModel.onExpandCampaignsSelector(false, selectedItem)
                                campaignsSelectionViewModel.getCandidatesByCampaign(selectedItem.campaignId)
                            }
                        ) {
                            Text(
                                text = selectedOption.campaignDescription,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = White
                            )
                        }
                    }
                }

            }
        }

        Spacer(modifier = Modifier.size(32.dp))

        if (campaignsSelectionUiState.showCandidateSelector) {
            CandidateSelector(candidatesSelectorIsExpanded, campaignsSelectionViewModel, campaignsSelectionUiState.candidatesList)
        }

        Spacer(modifier = Modifier.weight(1f))

        ToRegisteredUserListButton(campaignsSelectionUiState.canContinue, navController)

        Spacer(modifier = Modifier.size(40.dp))
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CandidateSelector(
    candidatesSelectorIsExpanded: Boolean,
    campaignsSelectionViewModel: CampaignsSelectionViewModel,
    candidatesList: List<GetCandidatesResponse>
) {

    var selectedItem by remember { mutableStateOf(GetCandidatesResponse()) }
    campaignsSelectionViewModel.evaluateIfCanContinue(selectedItem)

    Text(
        text = "Selecciona un candidato",
        fontSize = 18.sp,
        color = White,
        fontWeight = FontWeight.SemiBold
    )

    Spacer(modifier = Modifier.size(10.dp))

    Row(verticalAlignment = Alignment.CenterVertically) {
        ExposedDropdownMenuBox(
            modifier = Modifier
                .height(55.dp)
                .fillMaxWidth(),
            expanded = candidatesSelectorIsExpanded,
            onExpandedChange = {
                campaignsSelectionViewModel.onExpandCandidatesSelector(!candidatesSelectorIsExpanded, selectedItem)
            }
        ) {
            TextField(
                modifier = Modifier.exposedDropdownSize(),
                shape = RoundedCornerShape(5.dp),
                value = selectedItem.candidateName,
                onValueChange = { },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = candidatesSelectorIsExpanded) },
                placeholder = {
                    Text(
                        text = "Campaña",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = White
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = HighLightsColor,
                    focusedIndicatorColor = Transparent,
                    unfocusedIndicatorColor = Transparent,
                    trailingIconColor = White,
                    textColor = White
                )
            )

            DropdownMenu(
                modifier = Modifier
                    .background(HighLightsColor)
                    .exposedDropdownSize(),
                expanded = candidatesSelectorIsExpanded,
                onDismissRequest = { campaignsSelectionViewModel.onExpandCandidatesSelector(false, selectedItem) }
            ) {
                candidatesList.forEach { selectedOption ->
                    DropdownMenuItem(
                        onClick = {
                            selectedItem = selectedOption
                            campaignsSelectionViewModel.onExpandCandidatesSelector(false, selectedItem)
                        }
                    ) {
                        Text(
                            text = selectedOption.candidateName,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ToRegisteredUserListButton(
    continueEnable: Boolean,
    navController: NavController,
) {
    Button(
        onClick = {
            navController.navigate(route = AppScreens.HomeScreen.route)
        },
        enabled = continueEnable,
        modifier = Modifier
            .padding(20.dp, 0.dp)
            .fillMaxWidth()
            .height(45.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = HighLightsColor,
            contentColor = Color.White,
            disabledBackgroundColor = HighLightsDisabledColor,
            disabledContentColor = Color.White
        )
    ) {
        Text(
            text = "Continuar",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}