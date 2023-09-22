package app.android.seedapp.application.data.models.register

import com.google.gson.annotations.SerializedName

data class GetMunicipalityListResponse(
    @SerializedName("count") val municipalityCount: Int = 0,
    @SerializedName("next") val municipalityNextPage: String = "",
    @SerializedName("previous") val municipalityPreviousPage: String = "",
    @SerializedName("results") val municipalityList: List<MunicipalityDetails> = emptyList()
)

data class MunicipalityDetails(
    @SerializedName("id") val municipalityId: Int = -1,
    @SerializedName("name") val municipalityName: String = "",
    @SerializedName("status") val municipalityStatus: Boolean = true,
    @SerializedName("department") val municipalityDepartment: String = "",
)