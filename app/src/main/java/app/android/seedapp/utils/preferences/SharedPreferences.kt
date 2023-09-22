package app.android.seedapp.utils.preferences

import android.content.Context
import app.android.seedapp.application.data.models.campaigns.GetCandidatesResponse
import app.android.seedapp.application.data.models.campaigns.GetUserCampaignsResponse
import app.android.seedapp.application.data.models.registryoffice.GetUserInfoFromIdResponse
import app.android.seedapp.application.data.models.user.GetUserInfoResponse
import app.android.seedapp.utils.application.BaseApplication
import app.android.seedapp.utils.Constants
import com.google.gson.Gson
import javax.inject.Inject

class SharedPreferences @Inject constructor(
    application: BaseApplication
) : SharedPreferencesInterface {

    private val sharedPreferencesSeedApp =
        application.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    /** Tokens Start **/
    override fun saveFirebaseToken(token: String) {
        sharedPreferencesSeedApp.edit().putString(Constants.FIREBASE_TOKEN, token).apply()
    }

    override fun getFirebaseToken(): String {
        return sharedPreferencesSeedApp.getString(Constants.FIREBASE_TOKEN, "") ?: ""
    }

    override fun saveAppToken(token: String) {
        sharedPreferencesSeedApp.edit().putString(Constants.APPLICATION_TOKEN, token).apply()
    }

    override fun getAppToken(): String {
        return sharedPreferencesSeedApp.getString(Constants.APPLICATION_TOKEN, "") ?: ""
    }
    /** Tokens End **/

    /** Active Campaign Start **/
    override fun saveActiveCampaign(getUserCampaignsResponse: GetUserCampaignsResponse) {
        val getUserCampaignsResponseJson = Gson().toJson(getUserCampaignsResponse)
        sharedPreferencesSeedApp.edit().putString(Constants.ACTIVE_CAMPAIGN, getUserCampaignsResponseJson).apply()
    }

    override fun getActiveCampaign(): GetUserCampaignsResponse {
        val getUserCampaignsResponseJson = sharedPreferencesSeedApp.getString(Constants.ACTIVE_CAMPAIGN, "") ?: ""
        return Gson().fromJson(getUserCampaignsResponseJson, GetUserCampaignsResponse::class.java)
    }

    override fun saveActiveCandidate(getCandidatesResponse: GetCandidatesResponse) {
        val getCandidatesResponseJson = Gson().toJson(getCandidatesResponse)
        sharedPreferencesSeedApp.edit().putString(Constants.ACTIVE_CANDIDATE, getCandidatesResponseJson).apply()
    }

    override fun getActiveCandidate(): GetCandidatesResponse {
        val getCandidatesResponseJson = sharedPreferencesSeedApp.getString(Constants.ACTIVE_CANDIDATE, "") ?: ""
        return Gson().fromJson(getCandidatesResponseJson, GetCandidatesResponse::class.java)
    }
    /** Active Campaign End **/

    /** User info Start **/
    override fun saveUserInfo(getUserInfoResponse: GetUserInfoResponse) {
        val getUserInfoResponseJson = Gson().toJson(getUserInfoResponse)
        sharedPreferencesSeedApp.edit().putString(Constants.USER_INFO, getUserInfoResponseJson).apply()
    }

    override fun getUserInfo(): GetUserInfoResponse {
        val getUserInfoResponseJson = sharedPreferencesSeedApp.getString(Constants.USER_INFO, "") ?: ""
        return Gson().fromJson(getUserInfoResponseJson, GetUserInfoResponse::class.java)
    }
    /** User info End **/

    override fun saveUserIdInfo(getUserInfoFromIdResponse: GetUserInfoFromIdResponse) {
        val getUserInfoFromIdResponseJson = Gson().toJson(getUserInfoFromIdResponse)
        sharedPreferencesSeedApp.edit().putString(Constants.USER_ID_INFO, getUserInfoFromIdResponseJson).apply()
    }

    override fun getUserIdInfo(): GetUserInfoFromIdResponse {
        val getUserInfoFromIdResponseJson = sharedPreferencesSeedApp.getString(Constants.USER_ID_INFO, "") ?: ""
        return Gson().fromJson(getUserInfoFromIdResponseJson, GetUserInfoFromIdResponse::class.java)
    }
}