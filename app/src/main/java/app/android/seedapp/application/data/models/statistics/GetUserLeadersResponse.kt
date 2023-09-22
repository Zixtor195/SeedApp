package app.android.seedapp.application.data.models.statistics

import com.google.gson.annotations.SerializedName

data class GetUserLeadersResponse(
    @SerializedName("number_total_users") val numberTotalUsers: String? = "",
    @SerializedName("data_leaders") val dataLeaderList: ArrayList<LeaderData> = arrayListOf(),
)

data class LeaderData(
    @SerializedName("id_usr") val leaderPk: String = "",
    @SerializedName("identification") val leaderId: String? = "",
    @SerializedName("name") val leaderName: String = "",
    @SerializedName("count_users") val leaderRegisterCount: String? = "",
    @SerializedName("repet") val leaderIsRepeat: Boolean? = false
)