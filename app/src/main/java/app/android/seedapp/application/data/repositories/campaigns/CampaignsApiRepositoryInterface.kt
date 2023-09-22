package app.android.seedapp.application.data.repositories.campaigns

import app.android.seedapp.application.data.models.campaigns.GetCandidatesResponse
import app.android.seedapp.application.data.models.campaigns.GetUserCampaignsResponse
import app.android.seedapp.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface CampaignsApiRepositoryInterface {

    suspend fun getUserCampaigns(token: String): Flow<NetworkResult<List<GetUserCampaignsResponse>>>

    suspend fun getCandidatesByCampaign(token: String, idCampaign: Int): Flow<NetworkResult<List<GetCandidatesResponse>>>
}