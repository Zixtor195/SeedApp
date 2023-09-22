package app.android.seedapp.application.ui.viewmodels.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import app.android.seedapp.application.data.models.registryoffice.GetUserInfoFromIdResponse
import app.android.seedapp.application.usecases.registryoffice.GetUserInfoFromIdUseCase
import app.android.seedapp.utils.NetworkResult
import app.android.seedapp.utils.preferences.SharedPreferencesInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterUserIdViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferencesInterface,
    private val getUserInfoFromIdUseCase: GetUserInfoFromIdUseCase,
    app: Application
) : AndroidViewModel(app) {

    private val _getUserInfoFromId:
            MutableLiveData<NetworkResult<GetUserInfoFromIdResponse>> = MutableLiveData(NetworkResult.Unused())
    val getUserInfoFromId: LiveData<NetworkResult<GetUserInfoFromIdResponse>> = _getUserInfoFromId

    private val _numberDocument = MutableStateFlow("")
    val numberDocument: StateFlow<String> = _numberDocument.asStateFlow()

    private val _showCodeError = MutableStateFlow(false)
    val showCodeError: StateFlow<Boolean> = _showCodeError.asStateFlow()

    fun getUserInfoFromId(numberDocument: String) {
        viewModelScope.launch {
            _getUserInfoFromId.value = NetworkResult.Loading()
            getUserInfoFromIdUseCase.invoke(
                idCandidate = sharedPreferences.getActiveCandidate().candidateId.toString(),
                numberDocument = numberDocument
            ).collect { values ->
                _getUserInfoFromId.value = values
            }

        }
    }

    fun setShowCodeError(state: Boolean) {
        _showCodeError.value = state
    }

    fun saveUserIdInfo(getUserInfoFromIdResponse: GetUserInfoFromIdResponse) {
        sharedPreferences.saveUserIdInfo(getUserInfoFromIdResponse)
    }

    fun setUserId(id: String) {
        _numberDocument.value = id
    }
}
