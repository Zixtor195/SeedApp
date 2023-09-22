package app.android.seedapp.application.data.api

import app.android.seedapp.application.data.models.register.RegisterUserResponse
import app.android.seedapp.application.data.models.user.GetUserInfoResponse
import app.android.seedapp.application.data.models.user.GetUserReferredResponse
import app.android.seedapp.utils.Constants.HEADER_BASIC_AUTHORIZATION
import app.android.seedapp.utils.Constants.QUERY_ID_DOCUMENT
import app.android.seedapp.utils.Constants.QUERY_ID_KEY
import app.android.seedapp.utils.Constants.URL_GET_USER_INFO
import app.android.seedapp.utils.Constants.URL_GET_USER_REFERRED_LIST
import app.android.seedapp.utils.Constants.URL_REGISTER_USER
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface UserApi {

    @GET(URL_GET_USER_INFO)
    suspend fun getUserInfo(
        @Header(HEADER_BASIC_AUTHORIZATION) token: String
    ): Response<GetUserInfoResponse>

    @GET("$URL_GET_USER_REFERRED_LIST{$QUERY_ID_DOCUMENT}")
    suspend fun getUserReferredList(
        @Path(QUERY_ID_DOCUMENT) idCandidate: String,
        @Header(HEADER_BASIC_AUTHORIZATION) token: String
    ): Response<GetUserReferredResponse>

    @GET("$URL_REGISTER_USER{$QUERY_ID_KEY}")
    suspend fun getUserReferredInfo(
        @Path(QUERY_ID_KEY) idKey: String,
        @Header(HEADER_BASIC_AUTHORIZATION) token: String
    ): Response<RegisterUserResponse>


}