package app.android.seedapp.application.ui.viewmodels.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import app.android.seedapp.utils.Constants.LOGIN_SCREEN
import app.android.seedapp.utils.Constants.LOGIN_WEB_VIEW_SCREEN
import app.android.seedapp.utils.preferences.SharedPreferencesInterface
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginMainViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferencesInterface,
    app: Application
) : AndroidViewModel(app) {

    private val _userId = MutableStateFlow("")
    val userId: StateFlow<String> = _userId.asStateFlow()

    private val _validationEnable = MutableStateFlow(false)
    val validationEnable: StateFlow<Boolean> = _validationEnable.asStateFlow()

    private val _isExpanded = MutableStateFlow(false)
    val isExpanded: StateFlow<Boolean> = _isExpanded.asStateFlow()

    private val _termsAccepted = MutableStateFlow(false)
    val termsAccepted: StateFlow<Boolean> = _termsAccepted.asStateFlow()

    private val _isLogged = MutableStateFlow(false)
    val isLogged: StateFlow<Boolean> = _isLogged.asStateFlow()

    private val _loginMainNavController = MutableStateFlow(LOGIN_SCREEN)
    val loginMainNavController: StateFlow<String> = _loginMainNavController.asStateFlow()

    fun onUserIdChange(userId: String, isAccepted: Boolean) {
        _userId.value = userId
        _termsAccepted.value = isAccepted
        _validationEnable.value = isValidPassword(userId) && termsAccepted.value
    }

    private fun isValidPassword(userId: String): Boolean {
        return userId.isNotBlank()
    }


    fun onExpandIdentificationBox(isExpanded: Boolean) {
        _isExpanded.value = isExpanded
    }

    fun onDisconnectWebView() {
        _loginMainNavController.value = LOGIN_SCREEN
    }

    fun onClickShowTerms() {
        _loginMainNavController.value = LOGIN_WEB_VIEW_SCREEN
    }

    fun getAppToken() {
        _isLogged.value = sharedPreferences.getAppToken().isNotBlank()
    }

    fun getFirebaseToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.e("LoginMainViewModel", "Token generation Failed", task.exception)
                return@OnCompleteListener
            }
            Log.i("LoginMainViewModel", "Token generation Success", task.exception)
            sharedPreferences.saveFirebaseToken(task.result)
        })
    }

}