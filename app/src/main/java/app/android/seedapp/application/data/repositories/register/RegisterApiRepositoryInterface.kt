package app.android.seedapp.application.data.repositories.register

import app.android.seedapp.application.data.models.register.*
import app.android.seedapp.application.data.models.user.GetUserReferredResponse
import app.android.seedapp.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

interface RegisterApiRepositoryInterface {

    suspend fun getDepartmentList(token: String): Flow<NetworkResult<GetDepartmentListResponse>>

    suspend fun getMunicipalityList(token: String, departmentId: Int): Flow<NetworkResult<GetMunicipalityListResponse>>

    suspend fun sendConditionCodeToUser(
        token: String,
        sendConditionCodeToUserRequest: RequestBody
    ): Flow<NetworkResult<SendConditionCodeToUserResponse>>

    suspend fun registerUser(
        token: String,
        registerUserRequest: RegisterUserRequest
    ): Flow<NetworkResult<RegisterUserResponse>>


}