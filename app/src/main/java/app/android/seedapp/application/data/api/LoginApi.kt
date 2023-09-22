package app.android.seedapp.application.data.api

import app.android.seedapp.application.data.models.login.LoginUserCodeBodyRequest
import app.android.seedapp.application.data.models.login.LoginUserPasswordBodyRequest
import app.android.seedapp.application.data.models.login.LoginUserResponse
import app.android.seedapp.application.data.models.login.LoginValidationTypeResponse
import app.android.seedapp.utils.Constants.QUERY_NUMBER_DOCUMENT
import app.android.seedapp.utils.Constants.URL_USER_LOGIN
import app.android.seedapp.utils.Constants.URL_VALIDATE_TYPE_PASSWORD
import retrofit2.Response
import retrofit2.http.*


interface LoginApi {

    @GET(URL_VALIDATE_TYPE_PASSWORD)
    suspend fun validateTypePassword(
        @Query(QUERY_NUMBER_DOCUMENT) document: String
    ): Response<LoginValidationTypeResponse>

    @POST(URL_USER_LOGIN)
    suspend fun loginUserWithCode(
        @Body request: LoginUserCodeBodyRequest
    ): Response<LoginUserResponse>

    @POST(URL_USER_LOGIN)
    suspend fun loginUserWithPassword(
        @Body request: LoginUserPasswordBodyRequest
    ): Response<LoginUserResponse>

}
