package app.android.seedapp.application.ui.viewmodels.graphs.state

import app.android.seedapp.application.data.models.statistics.GetUserLeadersResponse

data class GraphsUiState(
    val maxValue: Int = 0,
    val keys: ArrayList<Float> = arrayListOf(),
    val data: ArrayList<String> = arrayListOf(),
    val value: ArrayList<Int> = arrayListOf(),
    val selectedOption: Int = 0,
    val userLeadersResponse: GetUserLeadersResponse = GetUserLeadersResponse()
)