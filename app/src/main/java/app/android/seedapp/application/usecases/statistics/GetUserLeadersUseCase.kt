package app.android.seedapp.application.usecases.statistics

import app.android.seedapp.application.data.models.statistics.GetUserLeadersResponse
import app.android.seedapp.utils.preferences.SharedPreferencesInterface
import app.android.seedapp.application.data.repositories.statistics.StatisticsApiRepositoryInterface
import app.android.seedapp.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserLeadersUseCase @Inject constructor(
    private val sharedPreferences: SharedPreferencesInterface,
    private val statisticsApiRepository: StatisticsApiRepositoryInterface
) {

    suspend operator fun invoke(
        userLeader: String,
        campaignId: String,
        isCandidate: Boolean
    ): Flow<NetworkResult<GetUserLeadersResponse>> {
        val token = sharedPreferences.getAppToken()
        return statisticsApiRepository.getUserLeaders(token, userLeader, campaignId, isCandidate)
    }

}