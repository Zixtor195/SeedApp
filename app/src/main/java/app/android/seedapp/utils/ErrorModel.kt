package app.android.seedapp.utils

import app.android.seedapp.application.data.models.registryoffice.GetUserInfoFromIdData
import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("error") val error: String? = "",
)