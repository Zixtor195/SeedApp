package app.android.seedapp.utils.preferences

import app.android.seedapp.application.data.models.campaigns.GetCandidatesResponse
import app.android.seedapp.application.data.models.campaigns.GetUserCampaignsResponse
import app.android.seedapp.application.data.models.registryoffice.GetUserInfoFromIdResponse
import app.android.seedapp.application.data.models.user.GetUserInfoResponse

interface SharedPreferencesInterface {

    fun saveFirebaseToken(token: String)

    fun getFirebaseToken(): String

    fun saveAppToken(token: String)

    fun getAppToken(): String

    fun saveActiveCampaign(getUserCampaignsResponse: GetUserCampaignsResponse)

    fun getActiveCampaign(): GetUserCampaignsResponse

    fun saveActiveCandidate(getCandidatesResponse: GetCandidatesResponse)

    fun getActiveCandidate(): GetCandidatesResponse

    fun saveUserInfo(getUserInfoResponse: GetUserInfoResponse)

    fun getUserInfo(): GetUserInfoResponse

    fun saveUserIdInfo(getUserInfoFromIdResponse: GetUserInfoFromIdResponse)

    fun getUserIdInfo(): GetUserInfoFromIdResponse

}