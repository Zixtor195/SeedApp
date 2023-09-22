package app.android.seedapp.application.usecases.statistics

import app.android.seedapp.application.data.models.statistics.GetUserCvsResponse
import app.android.seedapp.application.data.repositories.statistics.StatisticsApiRepositoryInterface
import app.android.seedapp.utils.NetworkResult
import app.android.seedapp.utils.preferences.SharedPreferencesInterface
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserCvsUseCase @Inject constructor(
    private val sharedPreferences: SharedPreferencesInterface,
    private val statisticsApiRepository: StatisticsApiRepositoryInterface
) {
    suspend operator fun invoke(
        idCandidate: String,
        isCandidate: Boolean,
    ): Flow<NetworkResult<GetUserCvsResponse>> {
        val token = sharedPreferences.getAppToken()
        return statisticsApiRepository.getUserCvs(token, idCandidate, isCandidate)
    }
}