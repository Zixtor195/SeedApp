package app.android.seedapp.application.data.models.login

import com.google.gson.annotations.SerializedName


data class LoginValidationTypeResponse(
    @SerializedName("success") val message: String = "",
    @SerializedName("error") val messageError: String = "",
    @SerializedName("require_pass") val requirePassword: Boolean = false
)


