package app.android.seedapp.application.data.repositories.statistics

import app.android.seedapp.application.data.api.StatisticsApi
import app.android.seedapp.application.data.models.statistics.GetUserCvsResponse
import app.android.seedapp.application.data.models.statistics.GetUserLeadersResponse
import app.android.seedapp.application.data.models.statistics.GetUserStatisticsRequest
import app.android.seedapp.application.data.models.statistics.GetUserStatisticsResponse
import app.android.seedapp.utils.BaseApiResponse
import app.android.seedapp.utils.Constants
import app.android.seedapp.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class StatisticsApiRepository @Inject constructor(
    private val statisticsApi: StatisticsApi
) : StatisticsApiRepositoryInterface, BaseApiResponse() {

    override suspend fun getUserStatistics(
        token: String,
        getUserStatisticsRequest: GetUserStatisticsRequest
    ): Flow<NetworkResult<GetUserStatisticsResponse>> {
        return flow {
            emit(safeApiCall {
                statisticsApi.getUserStatistics(
                    token = "${Constants.TOKEN_AUTHORIZATION} $token",
                    typeData = getUserStatisticsRequest.typeData,
                    startDate = getUserStatisticsRequest.startDate,
                    endDate = getUserStatisticsRequest.endDate,
                    campaignId = getUserStatisticsRequest.campaignId,
                    isCandidate = getUserStatisticsRequest.isCandidate
                )
            })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getUserLeaders(
        token: String,
        userLeader: String,
        campaignId: String,
        isCandidate: Boolean,
    ): Flow<NetworkResult<GetUserLeadersResponse>> {
        return flow {
            emit(safeApiCall {
                statisticsApi.getUserLeaders(
                    token = "${Constants.TOKEN_AUTHORIZATION} $token",
                    userLeader = userLeader,
                    campaignId = campaignId,
                    isCandidate = isCandidate
                )
            })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getUserCvs(
        token: String,
        idCandidate: String,
        isCandidate: Boolean
    ): Flow<NetworkResult<GetUserCvsResponse>> {
        return flow {
            emit(safeApiCall {
                statisticsApi.getUserCvs(
                    token = "${Constants.TOKEN_AUTHORIZATION} $token",
                    idCandidate = idCandidate,
                    isCandidate = isCandidate
                )
            })
        }.flowOn(Dispatchers.IO)
    }

}