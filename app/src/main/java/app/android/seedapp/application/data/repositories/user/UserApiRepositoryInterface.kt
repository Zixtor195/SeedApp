package app.android.seedapp.application.data.repositories.user


import app.android.seedapp.application.data.models.register.RegisterUserResponse
import app.android.seedapp.application.data.models.user.GetUserInfoResponse
import app.android.seedapp.application.data.models.user.GetUserReferredResponse
import app.android.seedapp.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface UserApiRepositoryInterface {

    suspend fun getUserInfo(token: String): Flow<NetworkResult<GetUserInfoResponse>>

    suspend fun getUserReferredList(
        token: String,
        idCandidate: String
    ): Flow<NetworkResult<GetUserReferredResponse>>

    suspend fun getUserReferredInfo(
        token: String,
        idKey: String
    ): Flow<NetworkResult<RegisterUserResponse>>
}