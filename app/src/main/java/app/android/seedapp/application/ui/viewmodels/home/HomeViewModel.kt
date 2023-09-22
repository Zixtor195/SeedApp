package app.android.seedapp.application.ui.viewmodels.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import app.android.seedapp.application.data.models.campaigns.GetCandidatesResponse
import app.android.seedapp.application.data.models.user.GetUserInfoResponse
import app.android.seedapp.application.data.models.user.GetUserReferredResponse
import app.android.seedapp.application.data.models.user.ReferredUserData
import app.android.seedapp.application.ui.viewmodels.home.state.HomeUiState
import app.android.seedapp.application.usecases.user.GetUserReferredListUseCase
import app.android.seedapp.utils.NetworkResult
import app.android.seedapp.utils.preferences.SharedPreferencesInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferencesInterface,
    private val getUserReferredListUseCase: GetUserReferredListUseCase,
    app: Application
) : AndroidViewModel(app) {

    private val _userReferredListResponse:
            MutableLiveData<NetworkResult<GetUserReferredResponse>> = MutableLiveData(NetworkResult.Unused())
    private val userReferredListResponse: LiveData<NetworkResult<GetUserReferredResponse>> = _userReferredListResponse

    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState: StateFlow<HomeUiState> = _homeUiState.asStateFlow()

    fun getUserReferredList() {
        viewModelScope.launch {
            _userReferredListResponse.value = NetworkResult.Loading()
            getUserReferredListUseCase.invoke(homeUiState.value.activeCandidateInfo.candidateId.toString()).collect { values ->
                _userReferredListResponse.value = values

                when (userReferredListResponse.value) {
                    is NetworkResult.Success -> {
                        Log.i("HomeViewModel", "UserReferredListResponse Work on Success")
                        userReferredListResponse.value?.data?.let {
                            setHomeUiState(userReferredList = it.referredList)
                        }
                    }
                    is NetworkResult.Loading -> {
                        Log.i("HomeViewModel", "UserReferredListResponse Work on Loading")
                    }
                    is NetworkResult.Error -> {
                        Log.e(
                            "HomeViewModel",
                            "UserReferredListResponse Work on error: " + userReferredListResponse.value?.message
                        )
                    }
                }
            }
        }
    }

    fun getUserInfo() {
        setHomeUiState(userInfo = sharedPreferences.getUserInfo())
    }

    fun getActiveCandidateInfo() {
        setHomeUiState(activeCandidateInfo = sharedPreferences.getActiveCandidate())
    }

    fun setShowDialogLogout(showDialogLogout: Boolean) {
        setHomeUiState(showDialogLogout = showDialogLogout)
    }

    fun closeSession() {
        sharedPreferences.saveAppToken("")
    }

    private fun setHomeUiState(
        userInfo: GetUserInfoResponse = homeUiState.value.userInfo,
        activeCandidateInfo: GetCandidatesResponse = homeUiState.value.activeCandidateInfo,
        userReferredList: List<ReferredUserData> = homeUiState.value.userReferredList,
        showDialogLogout: Boolean = homeUiState.value.showDialogLogout
    ) {
        _homeUiState.value = HomeUiState(
            userInfo = userInfo,
            activeCandidateInfo = activeCandidateInfo,
            userReferredList = userReferredList,
            showDialogLogout = showDialogLogout
        )
    }

}