package app.android.seedapp.application.data.models.register

import com.google.gson.annotations.SerializedName

data class SendConditionCodeToUserResponse(
    @SerializedName("success") val conditionState: String = ""
)