package app.android.seedapp.application.data.models.user

import com.google.gson.annotations.SerializedName

data class GetUserReferredResponse(
    @SerializedName("count") val referredCount: Int = 0,
    @SerializedName("next") val referredNextPage: String = "",
    @SerializedName("previous") val referredPreviousPage: String = "",
    @SerializedName("results") val referredList: List<ReferredUserData> = emptyList()
)

data class ReferredUserData(
    @SerializedName("pk") val primaryKey: String = "",
    @SerializedName("email") val userEmail: String = "",
    @SerializedName("first_name") val userFirstName: String = "",
    @SerializedName("last_name") val userLastName: String = "",
    @SerializedName("mobile") val userMobile: String = "",
    @SerializedName("phone") val userPhone: String = "",
    @SerializedName("genre") val userGenre: String = "",
    @SerializedName("type_document") val userTypeDocument: String = "",
    @SerializedName("number_document") val userNumberDocument: String = "",
    @SerializedName("birthday") val userBirthday: String = "",
    @SerializedName("avatar") val avatar: String = ""
)