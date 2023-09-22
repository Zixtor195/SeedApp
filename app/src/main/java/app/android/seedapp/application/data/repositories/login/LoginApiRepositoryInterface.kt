package app.android.seedapp.application.data.repositories.login

import app.android.seedapp.application.data.models.login.LoginUserCodeBodyRequest
import app.android.seedapp.application.data.models.login.LoginUserPasswordBodyRequest
import app.android.seedapp.application.data.models.login.LoginUserResponse
import app.android.seedapp.application.data.models.login.LoginValidationTypeResponse
import app.android.seedapp.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface LoginApiRepositoryInterface {

    suspend fun validateTypePassword(document: String): Flow<NetworkResult<LoginValidationTypeResponse>>

    suspend fun loginUserWithCode(loginUserCodeBodyRequest: LoginUserCodeBodyRequest): Flow<NetworkResult<LoginUserResponse>>

    suspend fun loginUserWithPassword(loginUserPasswordBodyRequest: LoginUserPasswordBodyRequest): Flow<NetworkResult<LoginUserResponse>>
}