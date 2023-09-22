package app.android.seedapp.application.data.models.login

import com.google.gson.annotations.SerializedName

data class LoginUserResponse(
    @SerializedName("token") val token: String = "",
    @SerializedName("pk") val userTokenApp: Int = 0
)