package app.android.seedapp.application.data.repositories.user

import app.android.seedapp.application.data.api.UserApi
import app.android.seedapp.application.data.models.register.RegisterUserResponse
import app.android.seedapp.application.data.models.user.GetUserInfoResponse
import app.android.seedapp.application.data.models.user.GetUserReferredResponse
import app.android.seedapp.utils.BaseApiResponse
import app.android.seedapp.utils.Constants
import app.android.seedapp.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserApiRepository @Inject constructor(
    private val userApi: UserApi
) : UserApiRepositoryInterface, BaseApiResponse() {

    override suspend fun getUserInfo(token: String): Flow<NetworkResult<GetUserInfoResponse>> {
        return flow {
            emit(safeApiCall {
                userApi.getUserInfo("${Constants.TOKEN_AUTHORIZATION} $token")
            })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getUserReferredList(token: String, idCandidate: String): Flow<NetworkResult<GetUserReferredResponse>> {
        return flow {
            emit(safeApiCall {
                userApi.getUserReferredList(idCandidate, "${Constants.TOKEN_AUTHORIZATION} $token")
            })
        }.flowOn(Dispatchers.IO)
    }


    override suspend fun getUserReferredInfo(token: String, idKey: String): Flow<NetworkResult<RegisterUserResponse>> {
        return flow {
            emit(safeApiCall {
                userApi.getUserReferredInfo(idKey, "${Constants.TOKEN_AUTHORIZATION} $token")
            })
        }.flowOn(Dispatchers.IO)
    }



}