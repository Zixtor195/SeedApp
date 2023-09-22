package app.android.seedapp.application.usecases.registryoffice

import app.android.seedapp.application.data.models.registryoffice.GetUserInfoFromIdResponse
import app.android.seedapp.application.data.repositories.registryoffice.RegistryOfficeApiRepositoryInterface
import app.android.seedapp.utils.NetworkResult
import app.android.seedapp.utils.preferences.SharedPreferencesInterface
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserInfoFromIdUseCase @Inject constructor(
    private val sharedPreferences: SharedPreferencesInterface,
    private val registryOfficeApiRepository: RegistryOfficeApiRepositoryInterface
) {
    suspend operator fun invoke(idCandidate: String, numberDocument: String): Flow<NetworkResult<GetUserInfoFromIdResponse>> {
        val token = sharedPreferences.getAppToken()
        return registryOfficeApiRepository.getUserInfoFromId(token, idCandidate, numberDocument)
    }
}