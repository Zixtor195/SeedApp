package app.android.seedapp.application.data.models.campaigns

import com.google.gson.annotations.SerializedName

data class GetUserCampaignsResponse(
    @SerializedName("id") val campaignId: Int = 0,
    @SerializedName("description") val campaignDescription: String = "",
    @SerializedName("start_created") val campaignStartDate: String = "",
    @SerializedName("end_created") val campaignEndDate: String = "",
    @SerializedName("is_active") val campaignIsActive: Boolean = true
)