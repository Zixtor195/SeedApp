package app.android.seedapp.application.data.models.user

import com.google.gson.annotations.SerializedName

data class GetUserInfoResponse(
    @SerializedName("pk") val userId: Int = 0,
    @SerializedName("email") val userEmail: String = "",
    @SerializedName("first_name") val userName: String = "",
    @SerializedName("last_name") val userLastName: String = "",
    @SerializedName("phone") val userPhone: String = "",
    @SerializedName("mobile") val userMobile: String = "",
    @SerializedName("avatar") val userAvatar: String = "",
    @SerializedName("genre") val userGenre: String = "",
    @SerializedName("type_document") val userTypeDocument: String = "",
    @SerializedName("number_document") val userNumberDocument: String = "",
    @SerializedName("birthday") val userBirthday: String = "",
    @SerializedName("isCandidate") val isCandidate: Boolean = false,
    @SerializedName("idCandidate") val idCandidate: String = ""
)