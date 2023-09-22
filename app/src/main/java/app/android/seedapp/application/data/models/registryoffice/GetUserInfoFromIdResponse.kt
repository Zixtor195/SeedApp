package app.android.seedapp.application.data.models.registryoffice

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class GetUserInfoFromIdResponse(
    @SerializedName("status") val status: String? = "",
    @SerializedName("errorMessage") val errorMessage: String? = "",
    @SerializedName("data") val data: GetUserInfoFromIdData? = GetUserInfoFromIdData()
)

data class GetUserInfoFromIdData(
    @SerializedName("cedula") val id: String? = "",
    @SerializedName("nombres") val firstName: String? = "",
    @SerializedName("apellidos") val lastName: String? = "",
    @SerializedName("antecedentes") val criminalRecord: String? = "",
    @SerializedName("departamento") val department: String? = "",
    @SerializedName("municipio") val municipality: String? = "",
    @SerializedName("zona") val zone: String? = "",
    @SerializedName("comuna") val communeCode: String? = "",
    @SerializedName("comunaNombre") val communeName: String? = "",
    @SerializedName("puesto") val votePlace: String? = "",
    @SerializedName("direccion") val voteAddress: String? = "",
    @SerializedName("mesa") val voteDesk: String? = ""
)