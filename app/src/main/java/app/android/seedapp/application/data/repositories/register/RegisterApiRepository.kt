package app.android.seedapp.application.data.repositories.register

import app.android.seedapp.application.data.api.RegisterApi
import app.android.seedapp.application.data.models.register.*
import app.android.seedapp.utils.BaseApiResponse
import app.android.seedapp.utils.Constants.TOKEN_AUTHORIZATION
import app.android.seedapp.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.RequestBody
import javax.inject.Inject

class RegisterApiRepository @Inject constructor(
    private val registerApi: RegisterApi
) : RegisterApiRepositoryInterface, BaseApiResponse() {

    override suspend fun getDepartmentList(token: String): Flow<NetworkResult<GetDepartmentListResponse>> {
        return flow {
            emit(safeApiCall {
                registerApi.getDepartmentList("$TOKEN_AUTHORIZATION $token")
            })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getMunicipalityList(
        token: String,
        departmentId: Int
    ): Flow<NetworkResult<GetMunicipalityListResponse>> {
        return flow {
            emit(safeApiCall {
                registerApi.getMunicipalityList("$TOKEN_AUTHORIZATION $token", departmentId)
            })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun sendConditionCodeToUser(
        token: String,
        sendConditionCodeToUserRequest: RequestBody
    ): Flow<NetworkResult<SendConditionCodeToUserResponse>> {
        return flow {
            emit(safeApiCall {
                registerApi.sendConditionCodeToUser("$TOKEN_AUTHORIZATION $token", sendConditionCodeToUserRequest)
            })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun registerUser(
        token: String,
        registerUserRequest: RegisterUserRequest
    ): Flow<NetworkResult<RegisterUserResponse>> {
        return flow {
            emit(safeApiCall {
                registerApi.registerUser("$TOKEN_AUTHORIZATION $token", registerUserRequest)
            })
        }.flowOn(Dispatchers.IO)
    }

}