package app.android.seedapp.application.data.repositories.registryoffice

import app.android.seedapp.application.data.api.RegistryOfficeApi
import app.android.seedapp.application.data.models.registryoffice.GetUserInfoFromIdResponse
import app.android.seedapp.utils.BaseApiResponse
import app.android.seedapp.utils.Constants.TOKEN_AUTHORIZATION
import app.android.seedapp.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RegistryOfficeApiRepository @Inject constructor(
    private val registryOfficeApi: RegistryOfficeApi
) : RegistryOfficeApiRepositoryInterface, BaseApiResponse() {

    override suspend fun getUserInfoFromId(
        token: String,
        idCandidate: String,
        numberDocument: String
    ): Flow<NetworkResult<GetUserInfoFromIdResponse>> {
        return flow {
            emit(safeApiCall {
                registryOfficeApi.getUserInfoFromId(
                    token = "$TOKEN_AUTHORIZATION $token",
                    idCandidate = idCandidate,
                    numberDocument = numberDocument,
                )
            })
        }.flowOn(Dispatchers.IO)
    }
}