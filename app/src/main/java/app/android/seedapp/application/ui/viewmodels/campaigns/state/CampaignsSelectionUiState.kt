package app.android.seedapp.application.ui.viewmodels.campaigns.state

import app.android.seedapp.application.data.models.campaigns.GetCandidatesResponse
import app.android.seedapp.application.data.models.campaigns.GetUserCampaignsResponse

data class CampaignsSelectionUiState(
    val showCampaignsSelector: Boolean = false,
    val showCandidateSelector: Boolean = false,
    val unauthorizedUser: Boolean = false,
    val canContinue: Boolean = false,
    val campaignSelectorIsExpanded: Boolean = false,
    val isCandidate: Boolean = false,
    val campaignsList: List<GetUserCampaignsResponse> = emptyList(),
    val candidatesList: List<GetCandidatesResponse> = emptyList()
)