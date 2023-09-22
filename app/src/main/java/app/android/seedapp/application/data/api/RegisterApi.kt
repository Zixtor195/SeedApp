package app.android.seedapp.application.data.api

import app.android.seedapp.application.data.models.register.*
import app.android.seedapp.utils.Constants.HEADER_BASIC_AUTHORIZATION
import app.android.seedapp.utils.Constants.QUERY_DEPARTMENT
import app.android.seedapp.utils.Constants.URL_GET_DEPARTMENT_LIST
import app.android.seedapp.utils.Constants.URL_GET_MUNICIPALITY_LIST
import app.android.seedapp.utils.Constants.URL_REGISTER_USER
import app.android.seedapp.utils.Constants.URL_SEND_CONDITION_CODE
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface RegisterApi {

    @GET(URL_GET_DEPARTMENT_LIST)
    suspend fun getDepartmentList(
        @Header(HEADER_BASIC_AUTHORIZATION) token: String
    ): Response<GetDepartmentListResponse>

    @GET(URL_GET_MUNICIPALITY_LIST)
    suspend fun getMunicipalityList(
        @Header(HEADER_BASIC_AUTHORIZATION) token: String,
        @Query(QUERY_DEPARTMENT) departmentId: Int
    ): Response<GetMunicipalityListResponse>

    @POST(URL_SEND_CONDITION_CODE)
    suspend fun sendConditionCodeToUser(
        @Header(HEADER_BASIC_AUTHORIZATION) token: String,
        @Body sendConditionCodeToUserRequest: RequestBody
    ): Response<SendConditionCodeToUserResponse>

    @POST(URL_REGISTER_USER)
    suspend fun registerUser(
        @Header(HEADER_BASIC_AUTHORIZATION) token: String,
        @Body request: RegisterUserRequest
    ): Response<RegisterUserResponse>


}