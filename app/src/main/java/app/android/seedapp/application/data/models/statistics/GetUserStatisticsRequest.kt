package app.android.seedapp.application.data.models.statistics

import com.google.gson.annotations.SerializedName

data class GetUserStatisticsRequest(
    @SerializedName("type_data") val typeData: String = "",
    @SerializedName("start_date") val startDate: String = "",
    @SerializedName("end_date") val endDate: String = "",
    @SerializedName("campaing_id") val campaignId: String = "",
    @SerializedName("isCandidate") val isCandidate: Boolean = false
)