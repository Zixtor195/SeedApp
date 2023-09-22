package app.android.seedapp.application.data.api

import app.android.seedapp.application.data.models.campaigns.GetCandidatesResponse
import app.android.seedapp.application.data.models.campaigns.GetUserCampaignsResponse
import app.android.seedapp.utils.Constants.HEADER_BASIC_AUTHORIZATION
import app.android.seedapp.utils.Constants.QUERY_ID_DOCUMENT
import app.android.seedapp.utils.Constants.URL_GET_CANDIDATES
import app.android.seedapp.utils.Constants.URL_GET_USER_CAMPAIGNS
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface CampaignsApi {

    @GET(URL_GET_USER_CAMPAIGNS)
    suspend fun getUserCampaigns(
        @Header(HEADER_BASIC_AUTHORIZATION) token: String
    ): Response<List<GetUserCampaignsResponse>>


    @GET("${URL_GET_CANDIDATES}{${QUERY_ID_DOCUMENT}}")
    suspend fun getCandidatesByCampaign(
        @Header(HEADER_BASIC_AUTHORIZATION) token: String,
        @Path(QUERY_ID_DOCUMENT) idCampaign: Int
    ): Response<List<GetCandidatesResponse>>

}