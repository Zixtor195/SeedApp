package app.android.seedapp.application.ui.viewmodels.home.state

import app.android.seedapp.application.data.models.campaigns.GetCandidatesResponse
import app.android.seedapp.application.data.models.user.GetUserInfoResponse
import app.android.seedapp.application.data.models.user.ReferredUserData

data class HomeUiState(
    val userInfo: GetUserInfoResponse = GetUserInfoResponse(),
    val activeCandidateInfo: GetCandidatesResponse = GetCandidatesResponse(),
    val userReferredList: List<ReferredUserData> = emptyList(),
    val showDialogLogout: Boolean = false
)