package app.android.seedapp.application.usecases.user

import app.android.seedapp.application.data.models.register.RegisterUserResponse
import app.android.seedapp.application.data.repositories.user.UserApiRepositoryInterface
import app.android.seedapp.utils.NetworkResult
import app.android.seedapp.utils.preferences.SharedPreferencesInterface
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserReferredInfoUseCase @Inject constructor(
    private val sharedPreferences: SharedPreferencesInterface,
    private val userInfoApiRepository: UserApiRepositoryInterface
) {

    suspend operator fun invoke(idKey: String): Flow<NetworkResult<RegisterUserResponse>> {
        val token = sharedPreferences.getAppToken()
        return userInfoApiRepository.getUserReferredInfo(token, idKey)
    }

}