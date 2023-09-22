package app.android.seedapp.application.data.models.statistics

import com.google.gson.annotations.SerializedName

data class GetUserCvsResponse(
    @SerializedName("success") val message: String = "",
    @SerializedName("error") val messageError: String = "",
)