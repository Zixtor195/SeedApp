package app.android.seedapp.application.data.api

import app.android.seedapp.application.data.models.registryoffice.GetUserInfoFromIdResponse
import app.android.seedapp.utils.Constants.HEADER_BASIC_AUTHORIZATION
import app.android.seedapp.utils.Constants.QUERY_ID_DOCUMENT_CANDIDATE
import app.android.seedapp.utils.Constants.QUERY_NUMBER_DOCUMENT
import app.android.seedapp.utils.Constants.URL_VALIDATE_NUMBER_ID
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RegistryOfficeApi {

    @GET(URL_VALIDATE_NUMBER_ID)
    suspend fun getUserInfoFromId(
        @Header(HEADER_BASIC_AUTHORIZATION) token: String,
        @Query(QUERY_ID_DOCUMENT_CANDIDATE) idCandidate: String,
        @Query(QUERY_NUMBER_DOCUMENT) numberDocument: String,
    ): Response<GetUserInfoFromIdResponse>

}