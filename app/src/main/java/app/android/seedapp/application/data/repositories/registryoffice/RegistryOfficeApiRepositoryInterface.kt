package app.android.seedapp.application.data.repositories.registryoffice

import app.android.seedapp.application.data.models.registryoffice.GetUserInfoFromIdResponse
import app.android.seedapp.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface RegistryOfficeApiRepositoryInterface {

    suspend fun getUserInfoFromId(
        token: String,
        idCandidate: String,
        numberDocument: String
    ): Flow<NetworkResult<GetUserInfoFromIdResponse>>
}