package app.android.seedapp.application.data.repositories.login


import app.android.seedapp.application.data.api.LoginApi
import app.android.seedapp.application.data.models.login.LoginUserCodeBodyRequest
import app.android.seedapp.application.data.models.login.LoginUserPasswordBodyRequest
import app.android.seedapp.application.data.models.login.LoginUserResponse
import app.android.seedapp.application.data.models.login.LoginValidationTypeResponse
import app.android.seedapp.utils.BaseApiResponse
import app.android.seedapp.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LoginApiRepository @Inject constructor(
    private val loginApi: LoginApi
) : LoginApiRepositoryInterface, BaseApiResponse() {

    override suspend fun validateTypePassword(document: String): Flow<NetworkResult<LoginValidationTypeResponse>> {
        return flow {
            emit(safeApiCall { loginApi.validateTypePassword(document) })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun loginUserWithCode(loginUserCodeBodyRequest: LoginUserCodeBodyRequest): Flow<NetworkResult<LoginUserResponse>> {
        return flow {
            emit(safeApiCall { loginApi.loginUserWithCode(loginUserCodeBodyRequest) })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun loginUserWithPassword(loginUserPasswordBodyRequest: LoginUserPasswordBodyRequest): Flow<NetworkResult<LoginUserResponse>> {
        return flow {
            emit(safeApiCall { loginApi.loginUserWithPassword(loginUserPasswordBodyRequest) })
        }.flowOn(Dispatchers.IO)
    }
}