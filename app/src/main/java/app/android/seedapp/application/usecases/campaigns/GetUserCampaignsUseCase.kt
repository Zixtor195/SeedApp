package app.android.seedapp.application.usecases.campaigns

import android.util.Log
import app.android.seedapp.application.data.models.campaigns.GetUserCampaignsResponse
import app.android.seedapp.application.data.repositories.campaigns.CampaignsApiRepositoryInterface
import app.android.seedapp.utils.NetworkResult
import app.android.seedapp.utils.preferences.SharedPreferencesInterface
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserCampaignsUseCase @Inject constructor(
    private val sharedPreferences: SharedPreferencesInterface,
    private val campaignsApiRepository: CampaignsApiRepositoryInterface
) {

    suspend operator fun invoke(): Flow<NetworkResult<List<GetUserCampaignsResponse>>> {
        val token = sharedPreferences.getAppToken()
        Log.e("Token", token)
        return campaignsApiRepository.getUserCampaigns(token)
    }
}