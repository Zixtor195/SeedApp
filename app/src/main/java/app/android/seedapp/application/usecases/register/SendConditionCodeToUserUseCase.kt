package app.android.seedapp.application.usecases.register

import android.util.Log
import app.android.seedapp.application.data.models.register.SendConditionCodeToUserResponse
import app.android.seedapp.application.data.repositories.register.RegisterApiRepositoryInterface
import app.android.seedapp.utils.NetworkResult
import app.android.seedapp.utils.preferences.SharedPreferencesInterface
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class SendConditionCodeToUserUseCase @Inject constructor(
    private val sharedPreferences: SharedPreferencesInterface,
    private val registerApiRepository: RegisterApiRepositoryInterface
) {
    suspend operator fun invoke(userMobile: String): Flow<NetworkResult<SendConditionCodeToUserResponse>> {
        val token = sharedPreferences.getAppToken()
        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("mobile", userMobile)
            .build()
        return registerApiRepository.sendConditionCodeToUser(token, requestBody)
    }
}