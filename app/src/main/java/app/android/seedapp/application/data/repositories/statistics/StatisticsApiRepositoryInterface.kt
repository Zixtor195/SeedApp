package app.android.seedapp.application.data.repositories.statistics

import app.android.seedapp.application.data.models.statistics.GetUserCvsResponse
import app.android.seedapp.application.data.models.statistics.GetUserLeadersResponse
import app.android.seedapp.application.data.models.statistics.GetUserStatisticsRequest
import app.android.seedapp.application.data.models.statistics.GetUserStatisticsResponse
import app.android.seedapp.utils.Constants
import app.android.seedapp.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query

interface StatisticsApiRepositoryInterface {

    suspend fun getUserStatistics(
        token: String,
        getUserStatisticsRequest: GetUserStatisticsRequest
    ): Flow<NetworkResult<GetUserStatisticsResponse>>

    suspend fun getUserLeaders(
        token: String,
        userLeader: String,
        campaignId: String,
        isCandidate: Boolean,
    ): Flow<NetworkResult<GetUserLeadersResponse>>

    suspend fun getUserCvs(
        token: String,
        idCandidate: String,
        isCandidate: Boolean,
    ): Flow<NetworkResult<GetUserCvsResponse>>
}