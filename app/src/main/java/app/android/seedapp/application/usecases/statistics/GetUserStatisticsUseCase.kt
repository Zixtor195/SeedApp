package app.android.seedapp.application.usecases.statistics

import app.android.seedapp.application.data.models.statistics.GetUserStatisticsRequest
import app.android.seedapp.application.data.models.statistics.GetUserStatisticsResponse
import app.android.seedapp.application.data.repositories.statistics.StatisticsApiRepositoryInterface
import app.android.seedapp.utils.NetworkResult
import app.android.seedapp.utils.preferences.SharedPreferencesInterface
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserStatisticsUseCase @Inject constructor(
    private val sharedPreferences: SharedPreferencesInterface,
    private val statisticsApiRepository: StatisticsApiRepositoryInterface
) {

    suspend operator fun invoke(getUserStatisticsRequest: GetUserStatisticsRequest): Flow<NetworkResult<GetUserStatisticsResponse>> {
        val token = sharedPreferences.getAppToken()
        return statisticsApiRepository.getUserStatistics(token, getUserStatisticsRequest)
    }

}