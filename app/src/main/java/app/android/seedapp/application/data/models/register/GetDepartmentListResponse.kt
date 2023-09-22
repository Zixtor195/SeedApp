package app.android.seedapp.application.data.models.register

import com.google.gson.annotations.SerializedName

data class GetDepartmentListResponse(
    @SerializedName("count") val departmentCount: Int = 0,
    @SerializedName("next") val departmentNextPage: String = "",
    @SerializedName("previous") val departmentPreviousPage: String = "",
    @SerializedName("results") val departmentList: List<DepartmentDetails> = emptyList()
)

data class DepartmentDetails(
    @SerializedName("id") val departmentId: Int = -1,
    @SerializedName("name") val departmentName: String = "",
    @SerializedName("region") val departmentRegion: String = "",
    @SerializedName("status") val departmentStatus: Boolean = true
)