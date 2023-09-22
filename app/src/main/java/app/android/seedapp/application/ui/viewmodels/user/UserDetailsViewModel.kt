package app.android.seedapp.application.ui.viewmodels.user

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import app.android.seedapp.application.data.models.campaigns.GetCandidatesResponse
import app.android.seedapp.application.data.models.register.RegisterUserResponse
import app.android.seedapp.application.data.models.user.GetUserInfoResponse
import app.android.seedapp.application.data.models.user.ReferredUserData
import app.android.seedapp.application.ui.viewmodels.home.state.HomeUiState
import app.android.seedapp.application.ui.viewmodels.user.state.UserDetailsUiState
import app.android.seedapp.application.usecases.user.GetUserReferredInfoUseCase
import app.android.seedapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val getUserReferredInfoUseCase: GetUserReferredInfoUseCase,
    app: Application
) : AndroidViewModel(app) {

    private val _userReferredInfoResponse:
            MutableLiveData<NetworkResult<RegisterUserResponse>> = MutableLiveData(NetworkResult.Unused())
    private val userReferredInfoResponse: LiveData<NetworkResult<RegisterUserResponse>> = _userReferredInfoResponse

    private val _userDetailsUiState = MutableStateFlow(UserDetailsUiState())
    val userDetailsUiState: StateFlow<UserDetailsUiState> = _userDetailsUiState.asStateFlow()


    fun getUserReferredInfo(idKey: String) {
        viewModelScope.launch {
            _userReferredInfoResponse.value = NetworkResult.Loading()
            getUserReferredInfoUseCase.invoke(idKey).collect { values ->
                _userReferredInfoResponse.value = values

                when (userReferredInfoResponse.value) {
                    is NetworkResult.Success -> {
                        Log.i("UserDetailsViewModel", "GetUserReferredInfo Work on Success")
                        userReferredInfoResponse.value?.data?.let {
                            setUserDetailsUiState(it)
                        }
                    }
                    is NetworkResult.Loading -> {
                        Log.i("UserDetailsViewModel", "GetUserReferredInfo Work on Loading")
                    }
                    is NetworkResult.Error -> {
                        Log.e(
                            "UserDetailsViewModel",
                            "GetUserReferredInfo Work on error: " + userReferredInfoResponse.value?.message
                        )
                    }
                }
            }
        }
    }

    private fun setUserDetailsUiState(
        registerUserResponse: RegisterUserResponse = userDetailsUiState.value.registerUserResponse,
    ) {
        _userDetailsUiState.value = UserDetailsUiState(
            registerUserResponse = registerUserResponse
        )
    }
}