package app.android.seedapp.application.data.models.login

import com.google.gson.annotations.SerializedName

data class LoginUserCodeBodyRequest(
    @SerializedName("number_document") val userId: String = "",
    @SerializedName("code_two_auth") val userAuthCode: String = "",
    @SerializedName("token_firebase") val userFirebaseToken: String = "",
    @SerializedName("device_firebase") val userDeviceType: String = ""
)
