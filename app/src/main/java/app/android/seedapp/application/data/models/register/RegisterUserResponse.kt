package app.android.seedapp.application.data.models.register

import com.google.gson.annotations.SerializedName

data class RegisterUserResponse(
    @SerializedName("email") val userEmail: String = "",
    @SerializedName("first_name") val userFirstName: String = "",
    @SerializedName("last_name") val userLastName: String = "",
    @SerializedName("mobile") val userMobile: String = "",
    @SerializedName("phone") val userPhone: String = "",
    @SerializedName("genre") val userGenre: String = "",
    @SerializedName("type_document") val userTypeDocument: String = "",
    @SerializedName("number_document") val userNumberDocument: String = "",
    @SerializedName("birthday") val userBirthday: String = "",
    @SerializedName("can_login") val userCanLogin: Boolean = true,
    @SerializedName("is_leader") val userIsLeader: Boolean = true,
    @SerializedName("rel_id_municipality") val userMunicipality: Int = 0,
    @SerializedName("user_first_lv") val userLevel: Int = 0,
    @SerializedName("place") val userVotePlace: String = "",
    @SerializedName("address") val userVoteAddress: String = "",
    @SerializedName("desk") val userVoteDesk: String = "",
    @SerializedName("code_generate") val userCodeConditions: String = ""
)