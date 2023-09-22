package app.android.seedapp.application.usecases.register

import android.util.Log
import app.android.seedapp.application.data.models.register.RegisterUserRequest
import app.android.seedapp.application.data.models.register.RegisterUserResponse
import app.android.seedapp.application.data.repositories.register.RegisterApiRepositoryInterface
import app.android.seedapp.utils.NetworkResult
import app.android.seedapp.utils.preferences.SharedPreferencesInterface
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val sharedPreferences: SharedPreferencesInterface,
    private val registerApiRepository: RegisterApiRepositoryInterface
) {
    suspend operator fun invoke(registerUserRequest: RegisterUserRequest): Flow<NetworkResult<RegisterUserResponse>> {
        val token = sharedPreferences.getAppToken()

        return registerApiRepository.registerUser(token, registerUserRequest)
    }
}