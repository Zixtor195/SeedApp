package app.android.seedapp.application.data.models.login

import com.google.gson.annotations.SerializedName

data class LoginUserPasswordBodyRequest(
    @SerializedName("number_document") val userId: String = "",
    @SerializedName("password") val userPassword: String = "",
    @SerializedName("token_firebase") val userFirebaseToken: String = "",
    @SerializedName("device_firebase") val userDeviceType: String = ""
)