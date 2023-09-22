package app.android.seedapp.application.ui.viewmodels.register

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import app.android.seedapp.application.data.models.register.*
import app.android.seedapp.application.data.models.registryoffice.GetUserInfoFromIdResponse
import app.android.seedapp.application.data.models.user.GetUserInfoResponse
import app.android.seedapp.application.usecases.register.GetDepartmentListUseCase
import app.android.seedapp.application.usecases.register.GetMunicipalityListUseCase
import app.android.seedapp.application.usecases.register.RegisterUserUseCase
import app.android.seedapp.application.usecases.register.SendConditionCodeToUserUseCase
import app.android.seedapp.navigation.AppScreens
import app.android.seedapp.utils.Constants.REGEX_EMAIL
import app.android.seedapp.utils.Constants.REGISTER_USER_CODE_SCREEN
import app.android.seedapp.utils.Constants.REGISTER_USER_SCREEN
import app.android.seedapp.utils.Constants.REGISTER_USER_WEB_VIEW_SCREEN
import app.android.seedapp.utils.NetworkResult
import app.android.seedapp.utils.preferences.SharedPreferencesInterface
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class RegisterUserViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferencesInterface,
    private val getDepartmentListUseCase: GetDepartmentListUseCase,
    private val getMunicipalityListUseCase: GetMunicipalityListUseCase,
    private val sendConditionCodeToUserUseCase: SendConditionCodeToUserUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    app: Application
) : AndroidViewModel(app) {
    private val context = app

    private val _departmentListResponse:
            MutableLiveData<NetworkResult<GetDepartmentListResponse>> = MutableLiveData(NetworkResult.Unused())
    private val departmentListResponse: LiveData<NetworkResult<GetDepartmentListResponse>> = _departmentListResponse

    private val _municipalityListResponse:
            MutableLiveData<NetworkResult<GetMunicipalityListResponse>> = MutableLiveData(NetworkResult.Unused())
    private val municipalityListResponse: LiveData<NetworkResult<GetMunicipalityListResponse>> = _municipalityListResponse

    private val _sendConditionCodeToUserResponse:
            MutableLiveData<NetworkResult<SendConditionCodeToUserResponse>> = MutableLiveData(NetworkResult.Unused())
    private val sendConditionCodeToUserResponse: LiveData<NetworkResult<SendConditionCodeToUserResponse>> =
        _sendConditionCodeToUserResponse

    private val _registerUserResponse:
            MutableLiveData<NetworkResult<RegisterUserResponse>> = MutableLiveData(NetworkResult.Unused())
    private val registerUserResponse: LiveData<NetworkResult<RegisterUserResponse>> = _registerUserResponse

    private val _departmentList = MutableStateFlow(emptyList<DepartmentDetails>())
    val departmentList: StateFlow<List<DepartmentDetails>> = _departmentList.asStateFlow()

    private val _departmentListIsExpanded = MutableStateFlow(false)
    val departmentListIsExpanded: StateFlow<Boolean> = _departmentListIsExpanded.asStateFlow()

    private val _municipalityList = MutableStateFlow(emptyList<MunicipalityDetails>())
    val municipalityList: StateFlow<List<MunicipalityDetails>> = _municipalityList.asStateFlow()

    private val _municipalityListIsExpanded = MutableStateFlow(false)
    val municipalityListIsExpanded: StateFlow<Boolean> = _municipalityListIsExpanded.asStateFlow()

    private val _registerUserRequest = MutableStateFlow(RegisterUserRequest())
    val registerUserRequest: StateFlow<RegisterUserRequest> = _registerUserRequest.asStateFlow()

    private val _registerNavController = MutableStateFlow(REGISTER_USER_SCREEN)
    val registerNavController: StateFlow<String> = _registerNavController.asStateFlow()

    private val _continueEnable = MutableStateFlow(false)
    val continueEnable: StateFlow<Boolean> = _continueEnable.asStateFlow()

    private val _selectedBirthDate = MutableStateFlow("")
    val selectedBirthDate: StateFlow<String> = _selectedBirthDate.asStateFlow()

    private val _selectedDepartment = MutableStateFlow(DepartmentDetails())
    val selectedDepartment: StateFlow<DepartmentDetails> = _selectedDepartment.asStateFlow()

    private val _selectedMunicipality = MutableStateFlow(MunicipalityDetails())
    val selectedMunicipality: StateFlow<MunicipalityDetails> = _selectedMunicipality.asStateFlow()

    private val _showCodeError = MutableStateFlow(false)
    val showCodeError: StateFlow<Boolean> = _showCodeError.asStateFlow()

    private val _userAge = MutableStateFlow(0)
    val userAge: StateFlow<Int> = _userAge.asStateFlow()

    fun getDepartmentList() {
        viewModelScope.launch {
            _departmentListResponse.value = NetworkResult.Loading()
            getDepartmentListUseCase.invoke().collect { values ->
                _departmentListResponse.value = values

                when (departmentListResponse.value) {
                    is NetworkResult.Success -> {
                        Log.i("RegisterUserViewModel", "GetDepartmentList Work on Success")
                        departmentListResponse.value?.data?.let {
                            val listDepartments = ArrayList<DepartmentDetails>()

                            it.departmentList.forEach { departmentDetails ->
                                listDepartments.add(departmentDetails)
                            }
                            _departmentList.value = listDepartments
                        }
                    }

                    is NetworkResult.Loading -> {
                        Log.i("RegisterUserViewModel", "GetDepartmentList Work on Loading")
                    }

                    is NetworkResult.Error -> {
                        Log.e(
                            "RegisterUserViewModel",
                            "GetDepartmentList Work on error: " + departmentListResponse.value?.message
                        )
                    }
                }
            }
        }
    }

    fun getMunicipalityList(departmentId: Int) {
        viewModelScope.launch {
            _municipalityListResponse.value = NetworkResult.Loading()
            getMunicipalityListUseCase.invoke(departmentId).collect { values ->
                _municipalityListResponse.value = values

                when (municipalityListResponse.value) {
                    is NetworkResult.Success -> {
                        Log.i("LoginAccessScreen", "GetMunicipalityList Work on Success")
                        municipalityListResponse.value?.data?.let {
                            val listMunicipality = ArrayList<MunicipalityDetails>()

                            it.municipalityList.forEach { municipalityDetails ->
                                listMunicipality.add(municipalityDetails)
                            }
                            _municipalityList.value = listMunicipality
                        }
                    }

                    is NetworkResult.Loading -> {
                        Log.i("LoginAccessScreen", "GetMunicipalityList Work on Loading")
                    }

                    is NetworkResult.Error -> {
                        Log.e(
                            "LoginAccessScreen",
                            "GetMunicipalityList Work on error: " + municipalityListResponse.value?.message
                        )
                    }
                }
            }
        }
    }

    fun sendConditionCodeToUser() {
        viewModelScope.launch {
            _sendConditionCodeToUserResponse.value = NetworkResult.Loading()
            sendConditionCodeToUserUseCase.invoke(_registerUserRequest.value.userMobile).collect { values ->
                _sendConditionCodeToUserResponse.value = values
                when (sendConditionCodeToUserResponse.value) {
                    is NetworkResult.Success -> {
                        Log.i("LoginAccessScreen", "SendConditionCodeToUserResponse Work on Success")
                        sendConditionCodeToUserResponse.value?.data?.let { }
                    }

                    is NetworkResult.Loading -> {
                        Log.i("LoginAccessScreen", "SendConditionCodeToUserResponse Work on Loading")
                    }

                    is NetworkResult.Error -> {
                        Log.e(
                            "LoginAccessScreen",
                            "SendConditionCodeToUserResponse Work on error: " + sendConditionCodeToUserResponse.value?.message
                        )
                    }
                }
            }
        }
    }

    fun registerUser(navController: NavHostController) {
        viewModelScope.launch {
            _registerUserResponse.value = NetworkResult.Loading()

            registerUserUseCase.invoke(_registerUserRequest.value).collect { values ->
                _registerUserResponse.value = values

                when (registerUserResponse.value) {
                    is NetworkResult.Success -> {
                        Log.i("RegisterUserViewModel", "RegisterUserResponse Work on Success")
                        registerUserResponse.value?.data?.let {
                            _showCodeError.value = false
                            Toast.makeText(context, "Usuario registrado de forma exitosa", Toast.LENGTH_LONG).show()
                            navController.popBackStack()
                            navController.navigate(AppScreens.HomeScreen.route)
                        }
                    }

                    is NetworkResult.Loading -> {
                        Log.i("RegisterUserViewModel", "RegisterUserResponse Work on Loading")
                    }

                    is NetworkResult.Error -> {
                        Log.e(
                            "RegisterUserViewModel",
                            "RegisterUserResponse Work on error: " + registerUserResponse.value?.message
                        )
                        _showCodeError.value = true
                    }
                }
            }
        }
    }

    fun registerUserRequestModifier(
        userEmail: String = registerUserRequest.value.userEmail,
        userFirstName: String = registerUserRequest.value.userFirstName,
        userLastName: String = registerUserRequest.value.userLastName,
        userMobile: String = registerUserRequest.value.userMobile,
        userPhone: String = registerUserRequest.value.userPhone,
        userGenre: String = registerUserRequest.value.userGenre,
        userTypeDocument: String = registerUserRequest.value.userTypeDocument,
        userNumberDocument: String = registerUserRequest.value.userNumberDocument,
        userBirthday: String = registerUserRequest.value.userBirthday,
        userCanLogin: Boolean = registerUserRequest.value.userCanLogin,
        userIsLeader: Boolean = registerUserRequest.value.userIsLeader,
        userMunicipality: Int = registerUserRequest.value.userMunicipality,
        //userDepartment: Int = registerUserRequest.value.userDepartment,
        userLevel: Int = registerUserRequest.value.userLevel,
        userVotePlace: String = registerUserRequest.value.userVotePlace,
        userAddress: String = registerUserRequest.value.userAddress,
        userVoteAddress: String = registerUserRequest.value.userVoteAddress,
        userVoteDesk: String = registerUserRequest.value.userVoteDesk,
        userCodeConditions: String = registerUserRequest.value.userCodeConditions,
    ) {
        _registerUserRequest.value = RegisterUserRequest(
            userEmail = userEmail,
            userFirstName = userFirstName,
            userLastName = userLastName,
            userMobile = userMobile,
            userPhone = userPhone,
            userGenre = userGenre,
            userTypeDocument = userTypeDocument,
            userNumberDocument = userNumberDocument,
            userBirthday = userBirthday,
            userCanLogin = userCanLogin,
            userIsLeader = userIsLeader,
            userMunicipality = userMunicipality,
            // userDepartment = userDepartment,
            userLevel = userLevel,
            userVotePlace = userVotePlace,
            userAddress = userAddress,
            userVoteAddress = userVoteAddress,
            userVoteDesk = userVoteDesk,
            userCodeConditions = userCodeConditions,
        )

        validateContinueToCodeButtonEnabled()
    }

    fun getActiveCandidate() {
        registerUserRequestModifier(userLevel = sharedPreferences.getActiveCandidate().candidateId)
        validateContinueToCodeButtonEnabled()
    }

    fun onExpandMunicipalityList(isExpanded: Boolean) {
        _municipalityListIsExpanded.value = isExpanded
    }

    fun onSelectMunicipality(index: Int) {
        val municipalityDetails = _municipalityListResponse.value?.data?.municipalityList?.get(index)
        setMunicipality(municipalityDetails ?: MunicipalityDetails())
        municipalityDetails?.let { registerUserRequestModifier(userMunicipality = it.municipalityId) }
        validateContinueToCodeButtonEnabled()
    }

    fun onSelectBirthDate(birthDate: String) {
        registerUserRequestModifier(userBirthday = birthDate)
        setBirthDate(birthDate)

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val from = LocalDate.parse(birthDate, formatter)
        val to = LocalDate.now()
        val period = Period.between(from, to)
        val years = period.years
        _userAge.value = years

        validateContinueToCodeButtonEnabled()
    }


    fun onExpandDepartmentList(isExpanded: Boolean) {
        _departmentListIsExpanded.value = isExpanded
    }

    fun onSelectDepartment(index: Int) {
        val departmentDetails = _departmentListResponse.value?.data?.departmentList?.get(index)
        setDepartment(departmentDetails ?: DepartmentDetails())
        departmentDetails?.departmentId?.let { getMunicipalityList(it) }
        //departmentList?.let { registerUserRequestModifier(userDepartment = it.departmentId) }
        validateContinueToCodeButtonEnabled()
    }

    fun onClickShowWebViewButton() {
        _registerNavController.value = REGISTER_USER_WEB_VIEW_SCREEN
    }

    fun onDisconnectWebView() {
        _registerNavController.value = REGISTER_USER_SCREEN
    }

    fun onClickContinueRegisterUserButton() {
        _registerNavController.value = REGISTER_USER_CODE_SCREEN
    }

    private fun setMunicipality(municipality: MunicipalityDetails) {
        _selectedMunicipality.value = municipality
    }

    private fun setDepartment(department: DepartmentDetails) {
        _selectedDepartment.value = department
    }

    private fun setBirthDate(birthDate: String) {
        _selectedBirthDate.value = birthDate
    }

    private fun validateContinueToCodeButtonEnabled() {
        _registerUserRequest.value.let {
            _continueEnable.value =
                !(it.userEmail.isBlank() ||
                        it.userFirstName.isBlank() ||
                        it.userLastName.isBlank() ||
                        it.userMobile.isBlank() ||
                        it.userGenre.isBlank() ||
                        it.userTypeDocument.isBlank() ||
                        it.userNumberDocument.isBlank() ||
                        it.userBirthday.isBlank() ||
                        it.userMunicipality == -1 ||
                        //it.userDepartment == -1 ||
                        it.userAddress.isBlank() ||
                        it.userVotePlace.isBlank() ||
                        it.userVoteAddress.isBlank() ||
                        it.userVoteDesk.isBlank()) &&
                        isValidEmail(it.userEmail) &&
                        _userAge.value > 18
        }
    }

    fun isValidEmail(emailToReview: String): Boolean {
        return if (emailToReview.isNotBlank()) {
            val emailRegex: Pattern = Pattern.compile(REGEX_EMAIL)
            emailRegex.matcher(emailToReview).matches()
        } else {
            false
        }
    }

    fun getUserIdInfo() {
        val getUserIdInfo = sharedPreferences.getUserIdInfo().data

        getUserIdInfo?.let {
            registerUserRequestModifier(
                userFirstName = it.firstName ?: "",
                userLastName = it.lastName ?: "",
                userNumberDocument = it.id ?: "",
                userVoteAddress = it.voteAddress ?: "",
                userVotePlace = it.votePlace ?: "",
                userVoteDesk = it.voteDesk ?: "",
            )

            //setDepartment(it.department ?: "")
            //setMunicipality(it.municipality ?: "")
        }
    }

}