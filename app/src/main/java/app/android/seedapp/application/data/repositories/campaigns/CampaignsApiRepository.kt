package app.android.seedapp.application.data.repositories.campaigns

import android.util.Log
import app.android.seedapp.application.data.api.CampaignsApi
import app.android.seedapp.application.data.models.campaigns.GetCandidatesResponse
import app.android.seedapp.application.data.models.campaigns.GetUserCampaignsResponse
import app.android.seedapp.utils.BaseApiResponse
import app.android.seedapp.utils.Constants
import app.android.seedapp.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CampaignsApiRepository @Inject constructor(
    private val campaignsApi: CampaignsApi
) : CampaignsApiRepositoryInterface, BaseApiResponse() {

    override suspend fun getUserCampaigns(token: String): Flow<NetworkResult<List<GetUserCampaignsResponse>>> {
        Log.e("Token", "${Constants.TOKEN_AUTHORIZATION} $token")
        return flow {
            emit(safeApiCall {
                campaignsApi.getUserCampaigns("${Constants.TOKEN_AUTHORIZATION} $token")
            })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getCandidatesByCampaign(
        token: String,
        idCampaign: Int
    ): Flow<NetworkResult<List<GetCandidatesResponse>>> {
        return flow {
            emit(safeApiCall {
                campaignsApi.getCandidatesByCampaign("${Constants.TOKEN_AUTHORIZATION} $token", idCampaign)
            })
        }.flowOn(Dispatchers.IO)
    }
}