package app.android.seedapp.application.data.api

import app.android.seedapp.application.data.models.statistics.GetUserCvsResponse
import app.android.seedapp.application.data.models.statistics.GetUserLeadersResponse
import app.android.seedapp.application.data.models.statistics.GetUserStatisticsResponse
import app.android.seedapp.utils.Constants.CAMPAING_ID
import app.android.seedapp.utils.Constants.END_DATE
import app.android.seedapp.utils.Constants.HEADER_BASIC_AUTHORIZATION
import app.android.seedapp.utils.Constants.IS_CANDIDATE
import app.android.seedapp.utils.Constants.START_DATE
import app.android.seedapp.utils.Constants.TYPE_DATA
import app.android.seedapp.utils.Constants.URL_GET_USER_CVS
import app.android.seedapp.utils.Constants.URL_GET_USER_LEADERS
import app.android.seedapp.utils.Constants.URL_GET_USER_STATISTICS
import app.android.seedapp.utils.Constants.USER_LEADER
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface StatisticsApi {

    @GET(URL_GET_USER_STATISTICS)
    suspend fun getUserStatistics(
        @Header(HEADER_BASIC_AUTHORIZATION) token: String,
        @Query(TYPE_DATA) typeData: String,
        @Query(START_DATE) startDate: String,
        @Query(END_DATE) endDate: String,
        @Query(CAMPAING_ID) campaignId: String,
        @Query(IS_CANDIDATE) isCandidate: Boolean,
    ): Response<GetUserStatisticsResponse>

    @GET(URL_GET_USER_LEADERS)
    suspend fun getUserLeaders(
        @Header(HEADER_BASIC_AUTHORIZATION) token: String,
        @Query(USER_LEADER) userLeader: String,
        @Query(CAMPAING_ID) campaignId: String,
        @Query(IS_CANDIDATE) isCandidate: Boolean,
    ): Response<GetUserLeadersResponse>


    @GET(URL_GET_USER_CVS)
    suspend fun getUserCvs(
        @Header(HEADER_BASIC_AUTHORIZATION) token: String,
        @Query(TYPE_DATA) idCandidate: String,
        @Query(START_DATE) isCandidate: Boolean,
    ): Response<GetUserCvsResponse>


}