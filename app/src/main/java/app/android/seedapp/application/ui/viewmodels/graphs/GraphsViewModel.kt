package app.android.seedapp.application.ui.viewmodels.graphs

import android.annotation.SuppressLint
import android.app.Application
import android.icu.text.SimpleDateFormat
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import app.android.seedapp.application.data.models.campaigns.GetCandidatesResponse
import app.android.seedapp.application.data.models.campaigns.GetUserCampaignsResponse
import app.android.seedapp.application.data.models.statistics.GetUserCvsResponse
import app.android.seedapp.application.data.models.statistics.GetUserLeadersResponse
import app.android.seedapp.application.data.models.statistics.GetUserStatisticsRequest
import app.android.seedapp.application.data.models.statistics.GetUserStatisticsResponse
import app.android.seedapp.application.data.models.user.GetUserInfoResponse
import app.android.seedapp.application.data.models.user.ReferredUserData
import app.android.seedapp.application.ui.viewmodels.graphs.state.GraphsUiState
import app.android.seedapp.application.ui.viewmodels.home.state.HomeUiState
import app.android.seedapp.application.usecases.statistics.GetUserCvsUseCase
import app.android.seedapp.application.usecases.statistics.GetUserLeadersUseCase
import app.android.seedapp.application.usecases.statistics.GetUserStatisticsUseCase
import app.android.seedapp.utils.NetworkResult
import app.android.seedapp.utils.preferences.SharedPreferencesInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Integer.parseInt
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

@HiltViewModel
class GraphsViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferencesInterface,
    private val getUserStatisticsUseCase: GetUserStatisticsUseCase,
    private val getUserLeadersUseCase: GetUserLeadersUseCase,
    private val getUserCvsUseCase: GetUserCvsUseCase,
    app: Application
) : AndroidViewModel(app) {
    private val context = app

    private val _getUserStatisticsResponse:
            MutableLiveData<NetworkResult<GetUserStatisticsResponse>> = MutableLiveData(NetworkResult.Unused())
    private val getUserStatisticsResponse: LiveData<NetworkResult<GetUserStatisticsResponse>> = _getUserStatisticsResponse

    private val _getUserLeadersResponse:
            MutableLiveData<NetworkResult<GetUserLeadersResponse>> = MutableLiveData(NetworkResult.Unused())
    private val getUserLeadersResponse: LiveData<NetworkResult<GetUserLeadersResponse>> = _getUserLeadersResponse

    private val _getUserCvsResponse:
            MutableLiveData<NetworkResult<GetUserCvsResponse>> = MutableLiveData(NetworkResult.Unused())
    private val getUserCvsResponse: LiveData<NetworkResult<GetUserCvsResponse>> = _getUserCvsResponse

    private val _graphsUiState = MutableStateFlow(GraphsUiState())
    val graphsUiState: StateFlow<GraphsUiState> = _graphsUiState.asStateFlow()

    @SuppressLint("SimpleDateFormat")
    fun getUserStatistics() {
        viewModelScope.launch {
            _getUserStatisticsResponse.value = NetworkResult.Loading()

            val formatDate = SimpleDateFormat("yyyy-M-dd")
            val currentDate = formatDate.format(Date())

            val request = GetUserStatisticsRequest(
                typeData = "day",
                startDate = "2000-01-01",
                endDate = currentDate,
                campaignId = getActiveCandidate().candidateId.toString(),
                isCandidate = getActiveCandidate().isCandidate
            )

            getUserStatisticsUseCase.invoke(request).collect { values ->
                _getUserStatisticsResponse.value = values

                when (getUserStatisticsResponse.value) {
                    is NetworkResult.Success -> {
                        Log.i("GraphsViewModel", "GetUserStatistics Work on Success")
                        getUserStatisticsResponse.value?.data?.let {

                            val data: ArrayList<String> = arrayListOf()
                            val registers: ArrayList<Int> = arrayListOf()
                            val keys: ArrayList<Float> = arrayListOf()

                            it.datesStatistics.forEach { datesStatistics ->
                                data.add(datesStatistics.date)
                                registers.add(datesStatistics.registers)
                            }

                            val average = registers.average()

                            val maxValue = if (average % 10 == 0.0) {
                                average
                            } else {
                                (10 - average % 10) + average
                            }

                            registers.forEach { number ->
                                val percentage = (number.toDouble() / maxValue)

                                keys.add(percentage.toFloat())

                                Log.i("GraphsViewModel", "key = ${percentage.toFloat()}")
                            }

                            setGraphsUiState(maxValue = maxValue.toInt(), keys = keys, data = data, value = registers)

                        }
                    }

                    is NetworkResult.Loading -> {
                        Log.i("GraphsViewModel", "GetUserStatistics Work on Loading")
                    }

                    is NetworkResult.Error -> {
                        Log.e(
                            "GraphsViewModel",
                            "GetUserStatistics Work on error: " + getUserStatisticsResponse.value?.message
                        )
                    }
                }
            }
        }
    }

    fun getUserLeaders() {
        viewModelScope.launch {
            _getUserLeadersResponse.value = NetworkResult.Loading()

            val isCandidate = getUserInfo().isCandidate
            val candidateId = getActiveCandidate().candidateId.toString()

            getUserLeadersUseCase.invoke("", candidateId, isCandidate).collect { values ->
                _getUserLeadersResponse.value = values

                when (getUserLeadersResponse.value) {
                    is NetworkResult.Success -> {
                        Log.i("GraphsViewModel", "GetUserLeaders Work on Success")
                        getUserLeadersResponse.value?.data?.let {
                            setGraphsUiState(userLeadersResponse = it)
                        }

                    }

                    is NetworkResult.Loading -> {
                        Log.i("GraphsViewModel", "GetUserLeaders Work on Loading")
                    }

                    is NetworkResult.Error -> {
                        Log.e(
                            "GraphsViewModel",
                            "GetUserLeaders Work on error: " + getUserLeadersResponse.value?.message
                        )
                    }
                }
            }
        }
    }

    fun getUserCvsResponse() {
        viewModelScope.launch {
            _getUserCvsResponse.value = NetworkResult.Loading()

            val isCandidate = getUserInfo().isCandidate
            val candidateId = getActiveCandidate().candidateId.toString()

            getUserCvsUseCase.invoke(candidateId, isCandidate).collect { values ->
                _getUserCvsResponse.value = values

                when (getUserCvsResponse.value) {
                    is NetworkResult.Success -> {
                        Log.i("GraphsViewModel", "GetUserCvs Work on Success")
                        getUserCvsResponse.value?.data?.let {

                        }

                        Toast.makeText(context, "Exportación en proceso en unos minutos se enviara a tu correo", Toast.LENGTH_LONG).show()

                    }

                    is NetworkResult.Loading -> {
                        Log.i("GraphsViewModel", "GetUserCvs Work on Loading")
                    }

                    is NetworkResult.Error -> {
                        Log.e(
                            "GraphsViewModel",
                            "GetUserCvs Work on error: " + getUserCvsResponse.value?.message
                        )
                    }
                }
            }
        }
    }

    fun getLeaderRegisters(leaderId: String) {
        viewModelScope.launch {
            _getUserLeadersResponse.value = NetworkResult.Loading()

            val isCandidate = getUserInfo().isCandidate
            val candidateId = getActiveCandidate().candidateId.toString()

            getUserLeadersUseCase.invoke(leaderId, candidateId, isCandidate).collect { values ->
                _getUserLeadersResponse.value = values

                when (getUserLeadersResponse.value) {
                    is NetworkResult.Success -> {
                        Log.i("GraphsViewModel", "GetUserLeaders Work on Success")
                        getUserLeadersResponse.value?.data?.let {
                            setGraphsUiState(userLeadersResponse = it)
                        }

                    }

                    is NetworkResult.Loading -> {
                        Log.i("GraphsViewModel", "GetUserLeaders Work on Loading")
                    }

                    is NetworkResult.Error -> {
                        Log.e(
                            "GraphsViewModel",
                            "GetUserLeaders Work on error: " + getUserLeadersResponse.value?.message
                        )
                    }
                }
            }
        }
    }

    fun getUserInfo(): GetUserInfoResponse {
        return sharedPreferences.getUserInfo()
    }


    fun getActiveCandidate(): GetCandidatesResponse {
        return sharedPreferences.getActiveCandidate()
    }

    fun getActiveCampaign(): GetUserCampaignsResponse {
        return sharedPreferences.getActiveCampaign()
    }

    fun setSelectedOption(selectedOption: Int) {
        setGraphsUiState(selectedOption = selectedOption)
    }

    private fun setGraphsUiState(
        maxValue: Int = graphsUiState.value.maxValue,
        keys: ArrayList<Float> = graphsUiState.value.keys,
        data: ArrayList<String> = graphsUiState.value.data,
        value: ArrayList<Int> = graphsUiState.value.value,
        selectedOption: Int = graphsUiState.value.selectedOption,
        userLeadersResponse: GetUserLeadersResponse = graphsUiState.value.userLeadersResponse
    ) {
        _graphsUiState.value = GraphsUiState(
            maxValue = maxValue,
            keys = keys,
            data = data,
            value = value,
            selectedOption = selectedOption,
            userLeadersResponse = userLeadersResponse
        )
    }
}