package app.android.seedapp.application.ui.viewmodels.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import app.android.seedapp.application.data.models.login.LoginUserResponse
import app.android.seedapp.application.data.models.login.LoginValidationTypeResponse
import app.android.seedapp.application.usecases.login.LoginUserUseCase
import app.android.seedapp.application.usecases.login.LoginValidationTypeUseCase
import app.android.seedapp.navigation.AppScreens
import app.android.seedapp.utils.NetworkResult
import app.android.seedapp.utils.preferences.SharedPreferencesInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginAccessViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferencesInterface,
    private val loginValidationTypeUseCase: LoginValidationTypeUseCase,
    private val loginUserUseCase: LoginUserUseCase,
    app: Application
) : AndroidViewModel(app) {

    private val _loginUser:
            MutableLiveData<NetworkResult<LoginUserResponse>> = MutableLiveData(NetworkResult.Loading())
    val loginUser: LiveData<NetworkResult<LoginUserResponse>> = _loginUser

    private val _validateTypePassword:
            MutableLiveData<NetworkResult<LoginValidationTypeResponse>> = MutableLiveData(NetworkResult.Loading())
    val validateTypePassword: LiveData<NetworkResult<LoginValidationTypeResponse>> = _validateTypePassword

    private val _userPassword = MutableStateFlow("")
    val userPassword: StateFlow<String> = _userPassword.asStateFlow()

    private val _loginEnable = MutableStateFlow(true)
    val loginEnable: StateFlow<Boolean> = _loginEnable.asStateFlow()

    private val _showLoginError= MutableStateFlow(false)
    val showLoginError: StateFlow<Boolean> = _showLoginError.asStateFlow()

    private val _authCode = MutableStateFlow("")
    val authCode: StateFlow<String> = _authCode.asStateFlow()

    fun validateTypePassword(document: String) {
        viewModelScope.launch {
            _validateTypePassword.value = NetworkResult.Loading()
            loginValidationTypeUseCase.invoke(document).collect { values ->
                _validateTypePassword.value = values
            }
        }
    }

    fun loginUser(
        document: String,
        userAuthCode: String,
        userPassword: String,
        requirePassword: Boolean,
        navController: NavController
    ) {
        val firebaseToken = getFirebaseToken()

        viewModelScope.launch {
            _loginUser.value = NetworkResult.Loading()
            loginUserUseCase.invoke(
                userId = document,
                userFirebaseToken = firebaseToken,
                userAuthCode = userAuthCode,
                userPassword = userPassword,
                requirePassword = requirePassword
            ).collect { values ->
                _loginUser.value = values

                when (loginUser.value) {
                    is NetworkResult.Success -> {
                        Log.i("LoginAccessScreen", "LoginUser Work on Success")
                        loginUser.value?.data?.let {
                            saveApplicationToken(it.token)
                            navController.popBackStack()
                            navController.navigate(route = AppScreens.CampaignsSelectionScreen.route)
                        }
                    }

                    is NetworkResult.Loading -> {
                        Log.i("LoginAccessScreen", "LoginUser Work on Loading")
                    }

                    is NetworkResult.Error -> {
                        Log.e("LoginAccessScreen", "LoginUser Work on error: " + loginUser.value?.message)
                        _showLoginError.value = true
                    }
                }
            }
        }
    }

    fun onUserAuthCodeChange(authCode: String) {
        _authCode.value = authCode
        _loginEnable.value = isValidCode(authCode)
    }

    fun onUserPasswordChange(password: String) {
        _userPassword.value = password
        _loginEnable.value = isValidPassword(password)
    }


    fun getFirebaseToken(): String = sharedPreferences.getFirebaseToken()

    private fun saveApplicationToken(token: String) = sharedPreferences.saveAppToken(token)

    private fun isValidCode(authCode: String): Boolean {
        return authCode.isNotBlank()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.isNotBlank()
    }
}