package app.android.seedapp.application.ui.viewmodels.campaigns

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import app.android.seedapp.application.data.models.campaigns.GetCandidatesResponse
import app.android.seedapp.application.data.models.campaigns.GetUserCampaignsResponse
import app.android.seedapp.application.data.models.user.GetUserInfoResponse
import app.android.seedapp.application.ui.screens.campaigns.CandidateSelector
import app.android.seedapp.application.ui.viewmodels.campaigns.state.CampaignsSelectionUiState
import app.android.seedapp.application.usecases.campaigns.GetCandidatesByCampaignUseCase
import app.android.seedapp.application.usecases.campaigns.GetUserCampaignsUseCase
import app.android.seedapp.application.usecases.user.GetUserInfoUseCase
import app.android.seedapp.utils.NetworkResult
import app.android.seedapp.utils.preferences.SharedPreferencesInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CampaignsSelectionViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferencesInterface,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getUserCampaignsUseCase: GetUserCampaignsUseCase,
    private val getCandidatesByCampaignUseCase: GetCandidatesByCampaignUseCase,
    app: Application
) : AndroidViewModel(app) {

    private val _userInfo:
            MutableLiveData<NetworkResult<GetUserInfoResponse>> = MutableLiveData(NetworkResult.Loading())
    private val userInfo: LiveData<NetworkResult<GetUserInfoResponse>> = _userInfo

    private val _userCampaignsList:
            MutableLiveData<NetworkResult<List<GetUserCampaignsResponse>>> = MutableLiveData(NetworkResult.Loading())
    private val userCampaignsList: LiveData<NetworkResult<List<GetUserCampaignsResponse>>> = _userCampaignsList

    private val _selectedCampaignCandidateList:
            MutableLiveData<NetworkResult<List<GetCandidatesResponse>>> = MutableLiveData(NetworkResult.Loading())
    private val selectedCampaignCandidateList: LiveData<NetworkResult<List<GetCandidatesResponse>>> =
        _selectedCampaignCandidateList

    private val _candidatesSelectorIsExpanded = MutableStateFlow(false)
    val candidatesSelectorIsExpanded: StateFlow<Boolean> = _candidatesSelectorIsExpanded.asStateFlow()

    private val _campaignsSelectionUiState = MutableStateFlow(CampaignsSelectionUiState())
    val campaignsSelectionUiState: StateFlow<CampaignsSelectionUiState> = _campaignsSelectionUiState.asStateFlow()

    private fun getUserInfo() {
        viewModelScope.launch {
            _userInfo.value = NetworkResult.Loading()
            getUserInfoUseCase.invoke().collect { values ->
                _userInfo.value = values

                when (userInfo.value) {
                    is NetworkResult.Success -> {
                        Log.i("RegisterUserViewModel", "GetUserInfo Work on Success")
                        userInfo.value?.data?.let {
                            saveUserInfo(it)

                            if (it.isCandidate) {
                                saveActiveCandidate(
                                    GetCandidatesResponse(
                                        isCandidate = true,
                                        candidateId = it.idCandidate.toInt(),
                                        candidateName = it.userName + " " + it.userLastName
                                    )
                                )

                                setCampaignsSelectionUiState(isCandidate = true)
                            }
                        }
                    }
                    is NetworkResult.Loading -> {
                        Log.i("RegisterUserViewModel", "GetUserInfo Work on Loading")
                    }
                    is NetworkResult.Error -> {
                        Log.e("RegisterUserViewModel", "GetUserInfo Work on error: " + userInfo.value?.message)
                    }
                }
            }
        }
    }


    fun getUserCampaigns() {
        viewModelScope.launch {
            _userCampaignsList.value = NetworkResult.Loading()
            getUserCampaignsUseCase.invoke().collect { values ->
                _userCampaignsList.value = values

                when (userCampaignsList.value) {
                    is NetworkResult.Success -> {
                        Log.i("CampaignSelectionScreen", "GetUserCampaigns Work on Success")
                        userCampaignsList.value?.data?.let { campaignsList ->
                            setCampaignsSelectionUiState(showCampaignsSelector = true, campaignsList = campaignsList)
                        }

                        getUserInfo()
                    }

                    is NetworkResult.Loading -> {
                        Log.i("CampaignSelectionScreen", "GetUserCampaigns Work on Loading")
                    }

                    is NetworkResult.Error -> {
                        Log.e("CampaignSelectionScreen", "GetUserCampaigns Work on error: " + userCampaignsList.value?.message)
                        if (userCampaignsList.value?.message == "Api call failed 401 Unauthorized") {
                            clearAppToken()
                            setCampaignsSelectionUiState(unauthorizedUser = true)
                        }
                    }
                }
            }
        }
    }

    fun getCandidatesByCampaign(idCampaign: Int) {
        viewModelScope.launch {
            _selectedCampaignCandidateList.value = NetworkResult.Loading()
            getCandidatesByCampaignUseCase.invoke(idCampaign).collect { values ->
                _selectedCampaignCandidateList.value = values

                when (selectedCampaignCandidateList.value) {
                    is NetworkResult.Success -> {
                        Log.i("CampaignSelectionScreen", "GetCandidatesByCampaign Work on Success")
                        selectedCampaignCandidateList.value?.data?.let { candidatesList ->
                            setCampaignsSelectionUiState(showCandidateSelector = true, candidatesList = candidatesList)
                        }
                    }

                    is NetworkResult.Loading -> {
                        Log.i("CampaignSelectionScreen", "GetCandidatesByCampaign Work on Loading")
                    }

                    is NetworkResult.Error -> {
                        Log.e(
                            "CampaignSelectionScreen",
                            "GetCandidatesByCampaign Work on error: " + (selectedCampaignCandidateList.value?.message)
                        )
                    }
                }
            }
        }
    }

    fun onExpandCampaignsSelector(isExpanded: Boolean, getUserCampaignsResponse: GetUserCampaignsResponse) {
        sharedPreferences.saveActiveCampaign(getUserCampaignsResponse)
        setCampaignsSelectionUiState(campaignSelectorIsExpanded = isExpanded)
    }

    fun onExpandCandidatesSelector(isExpanded: Boolean, getCandidatesResponse: GetCandidatesResponse) {
        saveActiveCandidate(getCandidatesResponse)
        _candidatesSelectorIsExpanded.value = isExpanded
    }

    private fun saveActiveCandidate(getCandidatesResponse: GetCandidatesResponse) {
        sharedPreferences.saveActiveCandidate(getCandidatesResponse)
    }

    private fun saveUserInfo(userInfo: GetUserInfoResponse) {
        sharedPreferences.saveUserInfo(userInfo)
    }

    private fun clearAppToken() {
        sharedPreferences.saveAppToken("")
    }

    fun evaluateIfCanContinue(
        getCandidatesResponse: GetCandidatesResponse
    ) {
        setCampaignsSelectionUiState(canContinue = getCandidatesResponse.candidateId != -1)
    }

    private fun setCampaignsSelectionUiState(
        showCampaignsSelector: Boolean = campaignsSelectionUiState.value.showCampaignsSelector,
        showCandidateSelector: Boolean = campaignsSelectionUiState.value.showCandidateSelector,
        unauthorizedUser: Boolean = campaignsSelectionUiState.value.unauthorizedUser,
        canContinue: Boolean = campaignsSelectionUiState.value.canContinue,
        campaignSelectorIsExpanded: Boolean = campaignsSelectionUiState.value.campaignSelectorIsExpanded,
        isCandidate: Boolean = campaignsSelectionUiState.value.isCandidate,
        campaignsList: List<GetUserCampaignsResponse> = campaignsSelectionUiState.value.campaignsList,
        candidatesList: List<GetCandidatesResponse> = campaignsSelectionUiState.value.candidatesList
    ) {
        _campaignsSelectionUiState.value = CampaignsSelectionUiState(
            showCampaignsSelector = showCampaignsSelector,
            showCandidateSelector = showCandidateSelector,
            unauthorizedUser = unauthorizedUser,
            canContinue = canContinue,
            campaignSelectorIsExpanded = campaignSelectorIsExpanded,
            isCandidate = isCandidate,
            campaignsList = campaignsList,
            candidatesList = candidatesList
        )
    }

}