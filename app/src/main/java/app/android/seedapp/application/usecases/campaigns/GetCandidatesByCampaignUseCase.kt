package app.android.seedapp.application.usecases.campaigns

import android.util.Log
import app.android.seedapp.application.data.models.campaigns.GetCandidatesResponse
import app.android.seedapp.application.data.repositories.campaigns.CampaignsApiRepositoryInterface
import app.android.seedapp.utils.NetworkResult
import app.android.seedapp.utils.preferences.SharedPreferencesInterface
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCandidatesByCampaignUseCase @Inject constructor(
    private val sharedPreferences: SharedPreferencesInterface,
    private val campaignsApiRepository: CampaignsApiRepositoryInterface
) {

    suspend operator fun invoke(idCampaign: Int): Flow<NetworkResult<List<GetCandidatesResponse>>> {
        val token = sharedPreferences.getAppToken()
        return campaignsApiRepository.getCandidatesByCampaign(token, idCampaign)
    }
}