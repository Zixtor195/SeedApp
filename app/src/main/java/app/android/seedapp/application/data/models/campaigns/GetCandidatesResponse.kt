package app.android.seedapp.application.data.models.campaigns

import com.google.gson.annotations.SerializedName

class GetCandidatesResponse(
    @SerializedName("id") val candidateId: Int = -1,
    @SerializedName("user") val candidateName: String = "",
    @SerializedName("isCandidate") val isCandidate: Boolean = false,
    @SerializedName("user_pk") val candidatePrimaryKey: String = "",
    @SerializedName("cDescription") val candidateCampaignDescription: String = "",
    @SerializedName("cCreated") val candidateCampaignCreation: String = "",
    @SerializedName("cIsactive") val candidateCampaignActive: Boolean = true,
    @SerializedName("rel_id_campaing") val candidateCampaignId: Int = -1,
    @SerializedName("rel_id_position") val candidatePositionId: Int = -1
)